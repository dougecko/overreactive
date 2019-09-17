package com.shinesolutions.overreactive.accounts.repository

import com.shinesolutions.overreactive.accounts.model.Account
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface NonReactiveAccountRepository : CrudRepository<Account, Int> {

    fun findAllByName(name: String): Iterable<Account> {
        return Account.ACCOUNTS
    }

}