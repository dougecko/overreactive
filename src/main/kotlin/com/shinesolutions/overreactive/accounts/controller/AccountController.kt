package com.shinesolutions.overreactive.accounts.controller

import com.shinesolutions.overreactive.accounts.model.Account
import com.shinesolutions.overreactive.accounts.repository.AccountRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.reactive.function.client.WebClient

@RestController
@RequestMapping("/accounts")
class AccountController() {

    @Autowired
    lateinit var webClient: WebClient

    @Autowired
    lateinit var accountRepository: AccountRepository

    ///// REACTIVE ENDPOINTS
    @FlowPreview
    @GetMapping
    suspend fun getAccountsList(): Flow<Account> = accountRepository.findAll()

//    @GetMapping("/{id}")
//    fun getAccount(@PathVariable("id") id: Long): Mono<Account> = accountService.findOne(id)
//
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping
//    fun createAccount(@RequestBody account: Account): Mono<Account> = accountService.create(account)

}