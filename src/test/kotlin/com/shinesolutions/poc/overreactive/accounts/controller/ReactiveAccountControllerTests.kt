package com.shinesolutions.poc.overreactive.accounts.controller

import com.nhaarman.mockitokotlin2.whenever
import com.shinesolutions.poc.overreactive.accounts.model.Account
import com.shinesolutions.poc.overreactive.accounts.service.AccountService
import com.shinesolutions.poc.overreactive.exceptions.ResourceNotFoundException
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@ExtendWith(SpringExtension::class)
@SpringBootTest
class ReactiveAccountControllerTests {

    @Mock
    lateinit var accountService: AccountService

    @Autowired
    lateinit var accountController: AccountController

    @BeforeEach
    fun init() {
        accountController = AccountController(accountService)
    }

    @Test
    fun whenGetAllAccounts_thenReactiveServiceFindAllCalled() {
        whenever(accountService.findAll()).thenReturn(Flux.just(Account.ACCOUNTS))
        val accounts: Flux<List<Account>> = accountController.getAccountsList()
        assertEquals(3, accounts.blockFirst()?.size)

        verify(accountService).findAll()
    }

    @Test
    fun whenGetIndividualAccount_thenReactiveServiceFindOneCalled() {
        whenever(accountService.findOne(1L)).thenReturn(Mono.just(Account.ACCOUNTS[0]))
        val account: Mono<Account> = accountController.getAccount(1L)
        assertEquals("Savings Account", account.block()?.name)
        verify(accountService).findOne(1L)
    }

    @Test
    fun whenGetUnknownIndividualAccount_thenReactiveServiceFindOneCalledAndExceptionThrown() {
        whenever(accountService.findOne(200L)).thenThrow(ResourceNotFoundException())
        assertThrows(ResourceNotFoundException::class.java) {
            accountController.getAccount(200L)
        }

        verify(accountService).findOne(200L)
    }
}