package com.shinesolutions.overreactive.accounts

import com.nhaarman.mockitokotlin2.given
import com.shinesolutions.overreactive.accounts.handler.AccountHandler
import com.shinesolutions.overreactive.accounts.model.Account
import com.shinesolutions.overreactive.accounts.repository.AccountRepository
import com.shinesolutions.overreactive.config.RouterConfiguration
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.reactive.flow.asFlow
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration
import org.springframework.boot.autoconfigure.security.reactive.ReactiveUserDetailsServiceAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBodyList
import reactor.core.publisher.Flux
import java.time.Instant
import java.time.LocalDateTime

@WebFluxTest(excludeAutoConfiguration = [ReactiveUserDetailsServiceAutoConfiguration::class, ReactiveSecurityAutoConfiguration::class])
@ExtendWith(SpringExtension::class)
@ContextConfiguration(classes = [AccountHandler::class, RouterConfiguration::class])
class AccountIntegrationTests {

    @Autowired
    private lateinit var client: WebTestClient

//    @MockBean
//    private lateinit var webClient: WebClient

    @MockBean
    private lateinit var accountRepository: AccountRepository

    private val accounts = listOf(
            Account(1, "Savings Account", 5000.55f, Instant.parse("2010-07-14T18:35:24.00Z")),
            Account(2, "My Platinum Reward VISA", -2000.00f, Instant.parse("2014-06-22T18:35:24.00Z")),
            Account(3, "Home Loan", -300000.00f, Instant.parse("2011-08-19T18:35:24.00Z"))
    )

    @FlowPreview
    @Test
    fun doAllTheStuff() {
//        val accounts: Flow<Account> = accountController.getAccountsList()
//
//        val accountList = accounts
//                .onEach { account -> println(account.toString()) }
//                .toList()
//
//        Assertions.assertEquals(3, accountList.size)

        val accountsFlow = Flux.fromIterable(accounts).asFlow()
        given(accountRepository.findAll()).willReturn(accountsFlow)
        client.get()
                .uri("/accounts")
                .exchange()
                .expectStatus()
                .isOk
                .expectBodyList<Account>()
    }

}