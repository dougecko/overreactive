package com.shinesolutions.overreactive.accounts.model

import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("credit_account")
data class CreditAccount(

        override val id: Long?,
        override val name: String,
        override val type: AccountType = AccountType.CREDIT,
        override var balance: Float,
        override val dateOpened: Instant,
        var statement: Statement,
        var hasRewards: Boolean,
        val accountNumber: String

) : Account(id = id, name = name, type = type, balance = balance, dateOpened = dateOpened)