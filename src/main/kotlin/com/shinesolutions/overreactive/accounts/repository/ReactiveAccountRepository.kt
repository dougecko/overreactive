package com.shinesolutions.overreactive.accounts.repository

import com.shinesolutions.overreactive.accounts.model.Account
import com.shinesolutions.overreactive.accounts.model.AccountType
import org.springframework.data.r2dbc.function.DatabaseClient
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono
import java.time.Instant

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

    fun insert(account: Account): Mono<Account> = client.insert()
            .into(Account::class.java)
            .using(account)
            .fetch().first()
            .map {
                Account(id = (it["id"] as Int).toLong(),
                        name = it["name"] as String,
                        type = AccountType.valueOf(it["type"] as String),
                        balance = (it["balance"] as Double).toFloat(),
                        dateOpened = it["date_opened"] as Instant
                )
            }
}