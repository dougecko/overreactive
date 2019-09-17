package com.shinesolutions.overreactive.accounts.controller

import com.shinesolutions.overreactive.accounts.model.Account
import com.shinesolutions.overreactive.accounts.service.NonReactiveAccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/nr/accounts")
class NonReactiveAccountController(@Autowired val accountService: NonReactiveAccountService) {
    ///// NON-REACTIVE ENDPOINTS
    @GetMapping
    fun getAccountsList(): List<Account> = accountService.findAll()

    @GetMapping("/{id}")
    fun getAccount(@PathVariable("id") id: Long) = accountService.findOne(id)

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    fun createAccount(@RequestBody account: Account) = accountService.create(account)
}