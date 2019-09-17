package com.shinesolutions.overreactive.accounts.repository

import com.shinesolutions.overreactive.accounts.model.Account
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
class AccountRepository {

    fun findAll(): Flux<Account> = Flux.fromIterable(Account.ACCOUNTS)
}