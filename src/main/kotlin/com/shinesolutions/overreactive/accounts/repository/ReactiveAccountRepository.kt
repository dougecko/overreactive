package com.shinesolutions.overreactive.accounts.repository

import com.shinesolutions.overreactive.accounts.model.Account
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.data.r2dbc.repository.query.Query
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux

@Repository
interface ReactiveAccountRepository : R2dbcRepository<Account, Long> {

    @Query("SELECT * FROM account WHERE name = $1")
    fun findAllByName(name: String): Flux<Account>
}