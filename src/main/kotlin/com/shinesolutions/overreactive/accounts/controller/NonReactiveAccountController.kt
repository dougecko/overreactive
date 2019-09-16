package com.shinesolutions.overreactive.accounts.controller

import com.shinesolutions.overreactive.accounts.model.Account
import com.shinesolutions.overreactive.accounts.service.NonReactiveAccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController("/nr/accounts")
class NonReactiveAccountController(@Autowired val accountService: NonReactiveAccountService) {
    ///// NON-REACTIVE ENDPOINTS
    @GetMapping("/nr/accounts")
    fun getAccountsList(): List<Account> = accountService.findAll()

    @GetMapping("/nr/accounts/{id}")
    fun getAccount(@PathVariable("id") id: Long) = accountService.findOne(id)

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/nr/accounts")
    fun createAccount(@RequestBody account: Account) = accountService.create(account)
}