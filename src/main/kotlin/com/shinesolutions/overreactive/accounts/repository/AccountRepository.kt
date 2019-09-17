package com.shinesolutions.overreactive.accounts.repository

import com.shinesolutions.overreactive.accounts.model.Account
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux

@Component
class AccountRepository {

    fun findAll(): Flux<Account> {
        return Flux.fromIterable(Account.ACCOUNTS)
    }

}