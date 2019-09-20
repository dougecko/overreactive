package com.shinesolutions.overreactive.accounts

import com.shinesolutions.overreactive.accounts.controller.ReactiveAccountController
import com.shinesolutions.overreactive.accounts.model.Account
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
    lateinit var accountController: ReactiveAccountController

    @Test
    fun doAllTheStuff() {
        val accounts: Flux<Account> = accountController.getAccountsList()

        val accountList = accounts
                .doOnNext { account -> println(account.toString()) }
                .doOnComplete { println("I'm done") }

        Assertions.assertEquals(3, accounts.collectList().block()?.size)
    }

}