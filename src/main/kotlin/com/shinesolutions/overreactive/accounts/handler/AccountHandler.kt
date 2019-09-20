package com.shinesolutions.overreactive.accounts.handler

import com.shinesolutions.overreactive.accounts.repository.AccountRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.server.ServerRequest
import org.springframework.web.reactive.function.server.ServerResponse
import org.springframework.web.reactive.function.server.bodyAndAwait
import org.springframework.web.reactive.function.server.json

@Component
class AccountHandler(
        @Autowired var webClient: WebClient,
        @Autowired var accountRepository: AccountRepository) {

    @ExperimentalCoroutinesApi
    @FlowPreview
    suspend fun findAll(request: ServerRequest): ServerResponse =
            ServerResponse.ok().json().bodyAndAwait(accountRepository.findAll())
}