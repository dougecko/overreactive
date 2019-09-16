package com.shinesolutions.poc.overreactive

import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class OverreactiveApplicationTests {

    @Test
    fun contextLoads() {
    }

    @Test
    fun getAllAccountsReactive() {
//        val accountServiceMock: AccountService = mock()
//        whenever(accountServiceMock.findAll()).thenReturn(Flux.just(Account.ACCOUNTS))
//        val nonReactiveAccountServiceMock: NonReactiveAccountService = mock() // unused
//        val accountController = AccountController(accountServiceMock, nonReactiveAccountServiceMock)
//        val accountsList = accountController.getAccountsList().blockFirst()
//
//        verify(accountServiceMock).findAll()
//        assert(accountsList?.size == 3) { "There should be three accounts" }
//		assert(accountsList?.get(0)?.name == "Savings Account") { "The first account should be Savings" }
	}

}
