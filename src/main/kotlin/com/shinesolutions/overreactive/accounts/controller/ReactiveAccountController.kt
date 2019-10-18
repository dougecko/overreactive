package com.shinesolutions.overreactive.accounts.controller

import com.shinesolutions.overreactive.accounts.model.Account
import com.shinesolutions.overreactive.accounts.repository.ReactiveAccountRepository
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/reactive/accounts")
class ReactiveAccountController(private val accountRepository: ReactiveAccountRepository) {

    @GetMapping
    fun getAccountsList(): Flux<Account> = accountRepository.findAll()

    @GetMapping("/{id}")
    fun getAccount(@PathVariable("id") id: Long): Mono<Account> = accountRepository.findById(id)

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createAccount(@RequestBody account: Account): Mono<Account> = accountRepository.save(account)

    @PostMapping("/{id}")
    fun updateAccount(@PathVariable("id") id: Long, @RequestBody account: Account): Mono<Account> {
        val updatedAccount = Account(id, account.name, account.type, account.balance, account.dateOpened)
        return accountRepository.save(updatedAccount)
    }

}
