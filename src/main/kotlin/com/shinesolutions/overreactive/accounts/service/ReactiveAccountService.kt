package com.shinesolutions.overreactive.accounts.service

import com.shinesolutions.overreactive.accounts.model.*
import com.shinesolutions.overreactive.accounts.repository.ReactiveAccountRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class ReactiveAccountService @Autowired constructor(private val accountRepository: ReactiveAccountRepository) {

    fun findAll(): Flux<Account> {
        return accountRepository.findAll()
    }

    fun getAccountById(id: Long): Mono<Account> {
        return accountRepository.getAccountById(id)
    }

//    fun findOne(id: Long): Mono<Account> = Mono.just(Account.ACCOUNTS.firstOrNull { account ->
//        account.id == id
//    } ?: throw ResourceNotFoundException())
//
//    fun create(account: Account): Mono<Account> {
//        val newAccount: Account
//
//        val newId = (Account.ACCOUNTS.size.toLong() + 1L)
//
//        newAccount = when (account.type) {
//
//            AccountType.DOMESTIC -> {
//                val domAccount = account as DomesticAccount
//                DomesticAccount(id = newId, name = account.name, balance = account.balance, dateOpened = account.dateOpened, accountId = domAccount.accountId, bsb = domAccount.bsb)
//            }
//            AccountType.CREDIT -> {
//                val creditAccount = account as CreditAccount
//                CreditAccount(id = newId, name = account.name, balance = account.balance, dateOpened = account.dateOpened, accountNumber = creditAccount.accountNumber, statement = creditAccount.statement, hasRewards = creditAccount.hasRewards)
//            }
//            AccountType.HOME_LOAN -> {
//                val homeLoanAccount = account as HomeLoanAccount
//                HomeLoanAccount(id = newId, name = account.name, balance = account.balance, dateOpened = account.dateOpened, nextRepaymentAmount = homeLoanAccount.nextRepaymentAmount, nextRepaymentDate = homeLoanAccount.nextRepaymentDate, repaymentTime = homeLoanAccount.repaymentTime)
//            }
//        }
//
//        Account.ACCOUNTS.add(newAccount)
//        return Mono.just(newAccount)
//    }
}