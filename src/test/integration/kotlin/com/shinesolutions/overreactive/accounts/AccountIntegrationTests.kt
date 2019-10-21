package com.shinesolutions.overreactive.accounts

import com.shinesolutions.overreactive.accounts.controller.ReactiveAccountController
import com.shinesolutions.overreactive.accounts.model.Account
import com.shinesolutions.overreactive.accounts.model.AccountType
import com.shinesolutions.overreactive.accounts.repository.ReactiveAccountRepository
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Flux
import java.time.LocalDate
import java.time.ZoneOffset

@ExtendWith(SpringExtension::class)
@SpringBootTest
class AccountIntegrationTests {

    @Autowired
    lateinit var accountController: ReactiveAccountController

    @Autowired
    lateinit var accountRepository: ReactiveAccountRepository

    @BeforeEach
    fun setup() {
        val firstAccount = Account(null, "Savings Account", AccountType.DOMESTIC, 5000.55f, LocalDate.parse("2010-07-14").atStartOfDay().toInstant(ZoneOffset.UTC))
        val secondAccount = Account(null, "My Platinum Reward VISA", AccountType.CREDIT, -2000.00f, LocalDate.parse("2014-06-22").atStartOfDay().toInstant(ZoneOffset.UTC))
        val thirdAccount = Account(null, "Home Loan", AccountType.HOME_LOAN, -300000.00f, LocalDate.parse("2011-08-19").atStartOfDay().toInstant(ZoneOffset.UTC))

        accountRepository.deleteAll().block()
        accountRepository.save(firstAccount).block()
        accountRepository.save(secondAccount).block()
        accountRepository.save(thirdAccount).block()
        org.assertj.core.api.Assertions.assertThat(accountRepository.findAll().count().block()).isEqualTo(3)
    }
    @Test
    fun doAllTheStuff() {
        val accounts: Flux<Account> = accountController.getAccountsList()

        val accountList = accounts
                .doOnNext { account -> println(account) }
                .doOnComplete { println("I'm done") }

        val actual = accounts.collectSortedList { o1, o2 -> o1.id!!.compareTo(o2.id!!) }.block()

        Assertions.assertEquals(3, actual?.size)
        // TODO assert without caring about id
//        Assertions.assertIterableEquals(
//                listOf(
//                        Account(id = 1, name = "Savings Account", balance = 5000.55f, type = AccountType.DOMESTIC, dateOpened = Instant.parse("2010-07-14T00:00:00Z")),
//                        Account(id = 2, name = "My Platinum Reward VISA", balance = -2000f, type = AccountType.CREDIT, dateOpened = Instant.parse("2014-06-22T00:00:00Z")),
//                        Account(id = 3, name = "Home Loan", balance = -300000f, type = AccountType.HOME_LOAN, dateOpened = Instant.parse("2011-08-19T00:00:00Z"))
//                ),
//                actual
//        )
    }

}