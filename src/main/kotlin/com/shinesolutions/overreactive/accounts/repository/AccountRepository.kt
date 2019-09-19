package com.shinesolutions.overreactive.accounts.repository

import com.shinesolutions.overreactive.accounts.model.Account
import org.springframework.data.r2dbc.repository.query.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface AccountRepository : ReactiveCrudRepository<Account, Int> {

    @Query("SELECT * FROM account")
    override fun findAll(): Flux<Account>

}