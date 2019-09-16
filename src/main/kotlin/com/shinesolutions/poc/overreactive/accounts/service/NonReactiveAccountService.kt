package com.shinesolutions.poc.overreactive.accounts.service

import com.shinesolutions.poc.overreactive.accounts.model.*
import com.shinesolutions.poc.overreactive.exceptions.ResourceNotFoundException
import org.springframework.stereotype.Component

@Component
class NonReactiveAccountService {
    fun findAll() = Account.ACCOUNTS

    fun findOne(id: Long) = Account.ACCOUNTS.firstOrNull { account ->
        account.id == id
    } ?: throw ResourceNotFoundException()

    fun create(account: Account): Account {
        val newAccount: Account

        val newId = (Account.ACCOUNTS.size.toLong() + 1L)

        newAccount = when (account.type) {

            AccountType.DOMESTIC -> {
                val domAccount = account as DomesticAccount
                DomesticAccount(id = newId, name = account.name, balance = account.balance, dateOpened = account.dateOpened, accountId = domAccount.accountId, bsb = domAccount.bsb)
            }
            AccountType.CREDIT -> {
                val creditAccount = account as CreditAccount
                CreditAccount(id = newId, name = account.name, balance = account.balance, dateOpened = account.dateOpened, accountNumber = creditAccount.accountNumber, statement = creditAccount.statement, hasRewards = creditAccount.hasRewards)
            }
            AccountType.HOME_LOAN -> {
                val homeLoanAccount = account as HomeLoanAccount
                HomeLoanAccount(id = newId, name = account.name, balance = account.balance, dateOpened = account.dateOpened, nextRepaymentAmount = homeLoanAccount.nextRepaymentAmount, nextRepaymentDate = homeLoanAccount.nextRepaymentDate, repaymentTime = homeLoanAccount.repaymentTime)
            }
        }

        Account.ACCOUNTS.add(newAccount)
        return newAccount
    }
}