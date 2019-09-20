package com.shinesolutions.overreactive.accounts.controller

import com.shinesolutions.overreactive.accounts.model.Account
import com.shinesolutions.overreactive.accounts.repository.ReactiveAccountRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController("/reactive/accounts")
class ReactiveAccountController(private val accountRepository: ReactiveAccountRepository) {

    @GetMapping
    fun getAccountsList(): Flux<Account> = accountRepository.findAll()

    @GetMapping("/{id}")
    fun getAccount(@PathVariable("id") id: Long): Mono<Account> = accountRepository.getAccountById(id)

//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping
//    fun createAccount(@RequestBody account: Account): Mono<Account> = accountService.create(account)

}