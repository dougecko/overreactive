package com.shinesolutions.overreactive.accounts.repository

import com.shinesolutions.overreactive.accounts.model.Account
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.data.r2dbc.repository.query.Query
import reactor.core.publisher.Flux

interface AccountRepository : R2dbcRepository<Account, Int> {

    @Query("SELECT * FROM account")
    override fun findAll(): Flux<Account>

}