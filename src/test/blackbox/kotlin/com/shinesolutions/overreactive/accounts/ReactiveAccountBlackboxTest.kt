package com.shinesolutions.overreactive.accounts

import com.shinesolutions.config.DatabaseConfig
import com.shinesolutions.overreactive.accounts.model.Account
import com.shinesolutions.overreactive.accounts.model.AccountType
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.core.io.Resource
import org.springframework.http.MediaType
import org.springframework.jdbc.datasource.init.ScriptUtils
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import java.sql.Connection
import java.time.Duration
import java.time.Instant

@Import(DatabaseConfig::class)
@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReactiveAccountBlackboxTest(@Autowired var webTestClient: WebTestClient, @Autowired var connection: Connection) {

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
                    Assertions.assertThat(response.responseBody).isNotNull()
                }
                .jsonPath("$.length()", 3)
    }

    @Test
    fun testGetAccountById() {
        webTestClient.get()
                .uri("/reactive/accounts/2")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk
                .expectBody()
                .consumeWith { response ->
                    Assertions.assertThat(response.responseBody).isNotNull()
                }
                .jsonPath("$.name").isEqualTo("My Platinum Reward VISA")
                .jsonPath("$.type").isEqualTo("CREDIT")
                .jsonPath("$.balance").isEqualTo(-2000f)
    }

    @Test
    fun testInsertAccount() {
        webTestClient.post()
                .uri("/reactive/accounts")
                .accept(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(Account(
                        name = "Fred",
                        type = AccountType.HOME_LOAN,
                        balance = -250000f,
                        dateOpened = Instant.now()
                )))
                .exchange()
                .expectStatus().isCreated
                .expectBody()
                .jsonPath("$.id").isNotEmpty
                .jsonPath("$.id").isNumber
                .jsonPath("$.name").isEqualTo("Fred")
                .jsonPath("$.balance").isEqualTo(-250000f)
    }
}