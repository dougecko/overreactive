package com.shinesolutions.overreactive.accounts.model

import org.springframework.data.annotation.Id
import java.time.Instant

data class Account(
        @Id
        var id: Long? = null,
        var name: String = "",
//        var type: AccountType = AccountType.DOMESTIC,
        var balance: Float = 0F,
        var dateOpened: Instant
)