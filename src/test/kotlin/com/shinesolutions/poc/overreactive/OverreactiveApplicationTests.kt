package com.shinesolutions.poc.overreactive

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.shinesolutions.poc.overreactive.accounts.controller.AccountController
import com.shinesolutions.poc.overreactive.accounts.model.Account
import com.shinesolutions.poc.overreactive.accounts.service.AccountService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.verify
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Flux

@ExtendWith(SpringExtension::class)
@SpringBootTest
class OverreactiveApplicationTests {

    @Test
    fun contextLoads() {
    }

    @Test
    fun getAllAccountsReactive() {
        val accountServiceMock: AccountService = mock()
        whenever(accountServiceMock.findAll()).thenReturn(Flux.just(Account.ACCOUNTS))
        val accountController = AccountController(accountServiceMock)
        val accountsList = accountController.getAccountsList().blockFirst()

        verify(accountServiceMock).findAll()
        assert(accountsList?.size == 3) { "There should be three accounts" }
        assert(accountsList?.get(0)?.name == "Savings Account") { "The first account should be Savings" }
    }

}
