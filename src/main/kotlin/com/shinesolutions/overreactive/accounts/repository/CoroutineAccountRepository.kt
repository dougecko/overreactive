package com.shinesolutions.overreactive.accounts.repository

import com.shinesolutions.overreactive.accounts.model.Account
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.stereotype.Repository

@Repository
class CoroutineAccountRepository(private val accountRepository: ReactiveAccountRepository) {

    suspend fun findAll(): Flow<Account> = accountRepository.findAll().asFlow()

    suspend fun findById(id: Long): Account = accountRepository.findById(id).awaitFirst()

    suspend fun save(account: Account): Account = accountRepository.save(account).awaitFirst()
}