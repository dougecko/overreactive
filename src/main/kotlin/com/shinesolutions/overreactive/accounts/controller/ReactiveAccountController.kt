package com.shinesolutions.overreactive.accounts.controller

import com.shinesolutions.overreactive.accounts.model.Account
import com.shinesolutions.overreactive.accounts.service.ReactiveAccountService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux

@RestController("/reactive/accounts")
class ReactiveAccountController(private val accountService: ReactiveAccountService) {

    @GetMapping
    fun getAccountsList(): Flux<Account> = accountService.findAll()

//    @GetMapping("/{id}")
//    fun getAccount(@PathVariable("id") id: Long): Mono<Account> = accountService.findOne(id)
//
//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping
//    fun createAccount(@RequestBody account: Account): Mono<Account> = accountService.create(account)

}