package com.shinesolutions.overreactive.accounts

import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import java.time.Duration

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ReactiveAccountBlackboxTest(@Autowired var webTestClient: WebTestClient) {

    @BeforeEach
    fun setup() {
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
                .consumeWith {response ->
                    Assertions.assertThat(response.responseBody).isNotNull()
                }
                .jsonPath("$.name", "My Platinum Reward VISA")
    }
}