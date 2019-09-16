package com.shinesolutions.poc.overreactive.unit.controller

import com.shinesolutions.poc.overreactive.accounts.model.Account
import com.shinesolutions.poc.overreactive.accounts.service.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController("/accounts")
class AccountController(@Autowired val accountService: AccountService) {

    ///// REACTIVE ENDPOINTS
    @GetMapping
    fun getAccountsList(): Flux<List<Account>> = accountService.findAll()

    @GetMapping("/{id}")
    fun getAccount(@PathVariable("id") id: Long): Mono<Account> = accountService.findOne(id)

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createAccount(@RequestBody account: Account): Mono<Account> = accountService.create(account)

}