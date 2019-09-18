package com.shinesolutions.overreactive.accounts.model

import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("domestic_account")
data class DomesticAccount(

        override val id: Long?,
        override val name: String,
        override var balance: Float = 0.0F,
        override var dateOpened: Instant = Instant.now(),
        val accountId: String,
        val bsb: String

) : Account(id = id, name = name, type = AccountType.DOMESTIC, balance = balance, dateOpened = dateOpened)