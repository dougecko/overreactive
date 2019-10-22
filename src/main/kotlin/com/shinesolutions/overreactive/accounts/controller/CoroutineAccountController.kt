package com.shinesolutions.overreactive.accounts.controller

import com.shinesolutions.overreactive.accounts.model.Account
import com.shinesolutions.overreactive.accounts.repository.CoroutineAccountRepository
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType.APPLICATION_STREAM_JSON_VALUE
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/coroutine/accounts")
class CoroutineAccountController(private val accountRepository: CoroutineAccountRepository) {

    @GetMapping(produces = [APPLICATION_STREAM_JSON_VALUE])
    suspend fun getAccountsList() = accountRepository.findAll()

    @GetMapping("/{id}")
    suspend fun getAccount(@PathVariable("id") id: Long): Account = accountRepository.findById(id)

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    suspend fun createAccount(@RequestBody account: Account): Account = accountRepository.save(account)

    @PostMapping("/{id}")
    suspend fun updateAccount(@PathVariable("id") id: Long, @RequestBody account: Account): Account {
        val updatedAccount = Account(id, account.name, account.type, account.balance, account.dateOpened)
        return accountRepository.save(updatedAccount)
    }
}
