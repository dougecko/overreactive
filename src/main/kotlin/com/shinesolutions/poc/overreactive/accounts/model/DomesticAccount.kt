package com.shinesolutions.poc.overreactive.accounts.model

import java.time.LocalDate

data class DomesticAccount(

        override val id: Long,
        override val name: String,
        override var balance: Float = 0.0F,
        override var dateOpened: LocalDate = LocalDate.now(),
        val accountId: String,
        val bsb: String

) : Account(id = id, name = name, type = AccountType.DOMESTIC, balance = balance, dateOpened = dateOpened)