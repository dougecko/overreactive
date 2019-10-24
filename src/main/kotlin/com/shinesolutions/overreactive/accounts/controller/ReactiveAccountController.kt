package com.shinesolutions.overreactive.accounts.controller

import com.shinesolutions.overreactive.accounts.model.Account
import com.shinesolutions.overreactive.accounts.repository.ReactiveAccountRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/reactive/accounts")
class ReactiveAccountController(private val accountRepository: ReactiveAccountRepository) {

    @GetMapping
    fun getAccountsList(): Flux<Account> = accountRepository.findAll()

    @GetMapping("/{id}")
    fun getAccount(@PathVariable("id") id: Long): Mono<Account> = accountRepository.getAccountById(id)

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createAccount(@RequestBody account: Account): Mono<Account> = accountRepository.insert(account)

}