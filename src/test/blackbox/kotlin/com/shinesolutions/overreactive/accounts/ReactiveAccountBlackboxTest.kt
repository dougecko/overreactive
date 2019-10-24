package com.shinesolutions.overreactive.accounts

import com.shinesolutions.overreactive.accounts.model.Account
import com.shinesolutions.overreactive.accounts.model.AccountType
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.jdbc.datasource.init.ScriptUtils
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import java.sql.Connection
import java.time.Duration
import java.time.Instant

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReactiveAccountBlackboxTest(@Autowired var webTestClient: WebTestClient, @Autowired val connection: Connection) {

    @Value("classpath:db/schema.sql")
    lateinit var schemaResource: Resource

    @Value("classpath:db/data.sql")
    lateinit var dataResource: Resource

    @BeforeEach
    fun setup() {
        ScriptUtils.executeSqlScript(connection, schemaResource)
        ScriptUtils.executeSqlScript(connection, dataResource)

        webTestClient = webTestClient.mutate()
                .responseTimeout(Duration.ofMillis(36000))
                .build()
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
                .jsonPath("$.length()").isEqualTo(3)
    }

    @Test
    fun testGetAccountById() {
        webTestClient.get()
                .uri("/reactive/accounts/{accountId}", 1)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .consumeWith {response ->
                    assertThat(response.responseBody).isNotNull()

                }
                .jsonPath("$.name").isEqualTo("Savings Account")
                .jsonPath("$.type").isEqualTo("DOMESTIC")
                .jsonPath("$.balance").isEqualTo(5000.55f)
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
                .jsonPath("$.length()").isEqualTo(4)
    }

    @Test
    fun testUpdateAccount() {
        val updatedAccount = Account(null, "updated account", AccountType.CREDIT, 99.99f, Instant.now())
        webTestClient.post()
                .uri("/reactive/accounts/{accountId}", 1)
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