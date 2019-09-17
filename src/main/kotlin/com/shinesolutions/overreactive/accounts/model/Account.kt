package com.shinesolutions.overreactive.accounts.model

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Table("account")
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", visible = true)
@JsonSubTypes(
        JsonSubTypes.Type(value = DomesticAccount::class, name = "DOMESTIC"),
        JsonSubTypes.Type(value = CreditAccount::class, name = "CREDIT"),
        JsonSubTypes.Type(value = HomeLoanAccount::class, name = "HOME_LOAN")
)
abstract class Account(open val id: Long?, open val name: String, open val type: AccountType, open val balance: Float, open val dateOpened: LocalDate) {

    companion object {
        private fun getLocalDate(date: String) = LocalDate.parse(date, DateTimeFormatter.ISO_DATE)

        private val STATEMENT = Statement(balance = 3000.00F, dueDate = getLocalDate("2018-09-13"))

        val ACCOUNTS = arrayListOf(
                getDomesticAccount(),
                getCreditAccount(),
                getHomeLoanAccount()
        )

        fun getDomesticAccount(id: Long? = 1L): DomesticAccount {
            return DomesticAccount(
                    id = id,
                    name = "Savings Account",
                    balance = 5000.55F,
                    dateOpened = getLocalDate("2010-07-14"),
                    accountId = "12345678",
                    bsb = "123-123")
        }

        fun getCreditAccount(id: Long? = 2L): CreditAccount {
            return CreditAccount(
                    id = id,
                    name = "My Platinum Reward VISA",
                    balance = -2000.00F,
                    dateOpened = getLocalDate("2014-06-22"),
                    statement = STATEMENT,
                    hasRewards = true,
                    accountNumber = "111111xxxxxx1111")
        }

        fun getHomeLoanAccount(id: Long? = 3L): HomeLoanAccount {
            return HomeLoanAccount(
                    id = id,
                    name = "Home Loan",
                    balance = -300000.00F,
                    dateOpened = getLocalDate("2011-08-19"),
                    repaymentTime = "29 years 4 months",
                    nextRepaymentAmount = 2000.00F,
                    nextRepaymentDate = getLocalDate("2018-09-27"))
        }

        fun resetAccounts() {
            ACCOUNTS.clear()
            ACCOUNTS.add(getDomesticAccount())
            ACCOUNTS.add(getCreditAccount())
            ACCOUNTS.add(getHomeLoanAccount())
        }
    }
}