package com.shinesolutions.overreactive.accounts

import com.shinesolutions.overreactive.accounts.model.Account
import com.shinesolutions.overreactive.accounts.model.AccountType
import com.shinesolutions.overreactive.accounts.repository.ReactiveAccountRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.Duration
import java.time.Instant

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReactiveAccountBlackboxTest(@Autowired var webTestClient: WebTestClient) {

    @Autowired
    lateinit var accountRepository: ReactiveAccountRepository

    @BeforeEach
    fun setup() {
        webTestClient = webTestClient.mutate()
                .responseTimeout(Duration.ofMillis(36000))
                .build()


        val firstAccount = Account(null, "first account", AccountType.CREDIT, 99.99f, Instant.now())
        val secondAccount = Account(null, "second account", AccountType.DOMESTIC, -20f, Instant.now())

        accountRepository.deleteAll().block()
        accountRepository.save(firstAccount).block()
        accountRepository.save(secondAccount).block()
        assertThat(accountRepository.findAll().count().block()).isEqualTo(2)
    }

    @Test
    fun testGetAllAccounts() {
        webTestClient.get()
                .uri("/reactive/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .consumeWith { response ->
                    assertThat(response.responseBody).isNotNull()
                }
                .jsonPath("$.length()").isEqualTo(2)
    }

    @Test
    fun testGetAccountById() {
        val account = accountRepository.findAll().blockFirst()
        webTestClient.get()
                .uri("/reactive/accounts/{accountId}", account!!.id)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .consumeWith {response ->
                    assertThat(response.responseBody).isNotNull()
                }
                .jsonPath("$.name").isEqualTo("first account")
                .jsonPath("$.type").isEqualTo("CREDIT")
                .jsonPath("$.balance").isEqualTo(99.99f)
    }

    @Test
    fun testCreateAccount() {
        val newAccount = Account(null, "new account", AccountType.DOMESTIC, -20f, Instant.now())
        webTestClient.post()
                .uri("/reactive/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(newAccount)
                .exchange()
                .expectStatus().isCreated
                .expectBody()
                .consumeWith {response ->
                    assertThat(response.responseBody).isNotNull()
                }
                .jsonPath("$.name").isEqualTo("new account")
                .jsonPath("$.type").isEqualTo("DOMESTIC")
                .jsonPath("$.balance").isEqualTo(-20f)

        webTestClient.get()
                .uri("/reactive/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .consumeWith { response ->
                    assertThat(response.responseBody).isNotNull()
                }
                .jsonPath("$.length()").isEqualTo(3)
    }

    @Test
    fun testUpdateAccount() {
        val account = accountRepository.findAll().blockFirst()
        val updatedAccount = Account(null, "updated account", AccountType.CREDIT, 99.99f, Instant.now())
        webTestClient.post()
                .uri("/reactive/accounts/{accountId}", account!!.id)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue(updatedAccount)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .consumeWith {response ->
                    assertThat(response.responseBody).isNotNull()
                }
                .jsonPath("$.name").isEqualTo("updated account")
    }
}