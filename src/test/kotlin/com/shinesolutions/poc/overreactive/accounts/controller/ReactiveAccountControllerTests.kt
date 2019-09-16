package com.shinesolutions.poc.overreactive.accounts.controller

import com.nhaarman.mockitokotlin2.whenever
import com.shinesolutions.poc.overreactive.accounts.model.Account
import com.shinesolutions.poc.overreactive.accounts.service.AccountService
import com.shinesolutions.poc.overreactive.accounts.service.NonReactiveAccountService
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Flux

@ExtendWith(SpringExtension::class)
@SpringBootTest
class ReactiveAccountControllerTests {

    @Mock
    lateinit var accountService: AccountService

    @Mock
    lateinit var nrAccountService: NonReactiveAccountService

    @Autowired
    lateinit var accountController: AccountController

    @BeforeEach
    fun init() {
        accountController = AccountController(accountService, nrAccountService)
    }

    @Test
    fun whenGetAllAccounts_thenReactiveServiceFindAllCalled() {
        whenever(accountService.findAll()).thenReturn(Flux.just(Account.ACCOUNTS))
        val accounts: Flux<List<Account>> = accountController.getAccountsList()
        assert(accounts.blockFirst()?.size == Account.ACCOUNTS.size) { "There should be ${Account.ACCOUNTS.size} accounts" }

        verify(accountService).findAll()
    }
}