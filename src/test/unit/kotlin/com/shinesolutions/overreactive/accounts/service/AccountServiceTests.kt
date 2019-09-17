package com.shinesolutions.overreactive.accounts.service

import com.nhaarman.mockitokotlin2.whenever
import com.shinesolutions.overreactive.accounts.model.Account
import com.shinesolutions.overreactive.accounts.repository.AccountRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Flux

@ExtendWith(SpringExtension::class)
@SpringBootTest
class AccountServiceTests {

    @Mock
    lateinit var repository: AccountRepository

    @Autowired
    lateinit var service: AccountService

    @Test
    fun whenFindAll_thenAllAccountsReturned() {
        whenever(repository.findAll()).thenReturn(Flux.fromIterable(Account.ACCOUNTS))
        service.findAll()
    }
}