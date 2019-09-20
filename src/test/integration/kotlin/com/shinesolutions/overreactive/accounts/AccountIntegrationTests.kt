package com.shinesolutions.overreactive.accounts

import com.shinesolutions.overreactive.accounts.controller.ReactiveAccountController
import com.shinesolutions.overreactive.accounts.model.Account
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.toList
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.time.Instant
import java.util.*

@ExtendWith(SpringExtension::class)
@SpringBootTest
class AccountIntegrationTests {

    @Autowired
    lateinit var accountController: ReactiveAccountController

    @FlowPreview
    @Test
    suspend fun doAllTheStuff() {
        val accounts: Flow<Account> = accountController.getAccountsList()

        val actual = emptyList<Account>().toMutableList()
        accounts.onEach { account -> println(account) }.toList(actual)
        actual.sortWith(Comparator { o1, o2 -> o1.id!!.compareTo(o2.id!!) })

        Assertions.assertEquals(3, actual.size)
        Assertions.assertIterableEquals(
                listOf(
                        Account(id = 1, name = "Savings Account", balance = 5000.55f, dateOpened = Instant.parse("2010-07-14T00:00:00Z")),
                        Account(id = 2, name = "My Platinum Reward VISA", balance = -2000f, dateOpened = Instant.parse("2014-06-22T00:00:00Z")),
                        Account(id = 3, name = "Home Loan", balance = -300000f, dateOpened = Instant.parse("2011-08-19T00:00:00Z"))
                ),
                actual
        )
    }

}