package com.shinesolutions.overreactive.accounts

import com.shinesolutions.overreactive.accounts.model.Account
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.core.ParameterizedTypeReference
import org.springframework.http.HttpMethod
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.web.client.RestTemplate

inline fun <reified T> typeReference() = object : ParameterizedTypeReference<T>() {}

@ExtendWith(SpringExtension::class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NonReactiveAccountBlackboxTest {

    @LocalServerPort
    var randomServerPort: Int = 0

    private lateinit var restTemplate: RestTemplate

    @BeforeEach
    fun setup() {
        restTemplate = RestTemplate()
    }

    private fun getBaseUri() = "http://localhost:$randomServerPort"

    private fun getUri(relativeUri: String = "") = getBaseUri() + relativeUri

    @Test
    fun testGetAllAccounts() {
        val response: ResponseEntity<List<Account>> = restTemplate
                .exchange(
                        getUri("/nr/accounts"),
                        HttpMethod.GET,
                        null,
                        typeReference<List<Account>>()
                )

        Assertions.assertThat(response.statusCode).isEqualTo(HttpStatus.OK)
        Assertions.assertThat(response.body).isNotNull
        Assertions.assertThat(response.body?.size).isEqualTo(3)
    }

}