package com.shinesolutions.overreactive.accounts.model

import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate

@Table("homeloan_account")
data class HomeLoanAccount(

        override val id: Long?,
        override val name: String,
        override val type: AccountType = AccountType.HOME_LOAN,
        override var balance: Float,
        override val dateOpened: LocalDate,
        var repaymentTime: String,
        var nextRepaymentAmount: Float,
        var nextRepaymentDate: LocalDate

) : Account(id = id, name = name, type = type, balance = balance, dateOpened = dateOpened)