package com.shinesolutions.overreactive

import com.shinesolutions.overreactive.accounts.controller.AccountController
import com.shinesolutions.overreactive.accounts.model.Account
import com.shinesolutions.overreactive.accounts.repository.AccountRepository
import com.shinesolutions.overreactive.accounts.service.AccountService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Flux

@ExtendWith(SpringExtension::class)
@SpringBootTest
class AccountIntegrationTests {

    @Autowired
    lateinit var accountController: AccountController

    @Test
    fun doStuff() {
        val accounts: Flux<Account> = accountController.getAccountsList()

        Assertions.assertEquals(3, accounts.collectList().block()?.size)
    }

}