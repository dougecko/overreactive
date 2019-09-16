package com.shinesolutions.poc.overreactive

import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import com.shinesolutions.poc.overreactive.accounts.controller.AccountController
import com.shinesolutions.poc.overreactive.accounts.model.Account
import com.shinesolutions.poc.overreactive.accounts.service.AccountService
import com.shinesolutions.poc.overreactive.accounts.service.NonReactiveAccountService
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner
import reactor.core.publisher.Flux

@RunWith(SpringRunner::class)
@SpringBootTest
class OverreactiveApplicationTests {

    @Test
    fun contextLoads() {
    }

    @Test
    fun getAllAccountsReactive() {
        val accountServiceMock: AccountService = mock()
        whenever(accountServiceMock.findAll()).thenReturn(Flux.just(Account.ACCOUNTS))
        val nonReactiveAccountServiceMock: NonReactiveAccountService = mock() // unused
        val accountController = AccountController(accountServiceMock, nonReactiveAccountServiceMock)
        val accountsList = accountController.getAccountsList().blockFirst()

        verify(accountServiceMock).findAll()
        assert(accountsList?.size == 3) { "There should be three accounts" }
		assert(accountsList?.get(0)?.name == "Savings Account") { "The first account should be Savings" }
	}

}
