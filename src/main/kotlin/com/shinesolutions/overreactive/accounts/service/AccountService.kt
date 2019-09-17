package com.shinesolutions.overreactive.accounts.service

import com.shinesolutions.overreactive.accounts.model.AccountType
import com.shinesolutions.overreactive.accounts.model.Account
import com.shinesolutions.overreactive.accounts.model.CreditAccount
import com.shinesolutions.overreactive.accounts.model.DomesticAccount
import com.shinesolutions.overreactive.accounts.model.HomeLoanAccount
import com.shinesolutions.overreactive.accounts.repository.AccountRepository
import com.shinesolutions.overreactive.exceptions.ResourceNotFoundException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class AccountService @Autowired constructor(
        private val accountRepository: AccountRepository
) {

    fun findAll(): Flux<Account> {
        return accountRepository.findAll()
    }

    fun findOne(id: Long): Mono<Account> = Mono.just(Account.ACCOUNTS.firstOrNull { account ->
        account.id == id
    } ?: throw ResourceNotFoundException())

    fun create(account: Account): Mono<Account> {
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
        return Mono.just(newAccount)
    }
}