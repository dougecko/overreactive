package com.shinesolutions.overreactive.accounts.repository

import com.shinesolutions.overreactive.accounts.model.Account
import org.springframework.data.r2dbc.function.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class ReactiveAccountRepository(private val client: DatabaseClient) {

    fun getAccountById(id: Long): Mono<Account> {
        return client.execute()
                .sql("SELECT * FROM account WHERE id = $1")
                .bind("$1", id)
                .`as`(Account::class.java)
                .fetch()
                .one()
    }

    fun findAll(): Flux<Account> {
        return client.select()
                .from("account")
                .`as`(Account::class.java)
                .fetch()
                .all()
    }
}