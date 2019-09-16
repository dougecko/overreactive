package com.shinesolutions.poc.overreactive.accounts.model

import java.time.LocalDate

data class CreditAccount(

        override val id: Long,
        override val name: String,
        override val type: AccountType = AccountType.CREDIT,
        override var balance: Float,
        override val dateOpened: LocalDate,
        var statement: Statement,
        var hasRewards: Boolean,
        val accountNumber: String

) : Account(id = id, name = name, type = type, balance = balance, dateOpened = dateOpened)