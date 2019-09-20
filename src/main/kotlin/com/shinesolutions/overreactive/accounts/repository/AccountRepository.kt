package com.shinesolutions.overreactive.accounts.repository

import com.shinesolutions.overreactive.accounts.model.Account
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.flow.asFlow
import org.springframework.data.r2dbc.function.DatabaseClient
import org.springframework.stereotype.Repository

@Repository
class AccountRepository(private val client: DatabaseClient) {

    @FlowPreview
    fun findAll(): Flow<Account> = client.select()
            .from("accounts")
            .`as`(Account::class.java)
            .fetch()
            .all()
            .log()
            .asFlow()
}