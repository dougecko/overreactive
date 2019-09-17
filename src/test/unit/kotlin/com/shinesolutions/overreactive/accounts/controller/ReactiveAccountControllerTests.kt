package com.shinesolutions.overreactive.accounts.controller

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import com.shinesolutions.overreactive.accounts.model.Account
import com.shinesolutions.overreactive.accounts.service.AccountService
import com.shinesolutions.overreactive.accounts.service.AccountServiceTests
import com.shinesolutions.overreactive.exceptions.ResourceNotFoundException
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@ExtendWith(SpringExtension::class)
@SpringBootTest
class ReactiveAccountControllerTests {

    @Mock
    lateinit var accountService: AccountService

    @Autowired
    lateinit var accountController: AccountController

    @BeforeEach
    fun init() {
        accountController = AccountController(accountService)
        Account.resetAccounts()
    }

    @Test
    fun whenGetAllAccounts_thenReactiveServiceFindAllCalled() {
        whenever(accountService.findAll()).thenReturn(Flux.fromIterable(Account.ACCOUNTS))
        val accounts: Flux<Account> = accountController.getAccountsList()
        assertEquals(3, accounts.toIterable().count())

        verify(accountService).findAll()
    }

    @Test
    fun whenGetIndividualAccount_thenReactiveServiceFindOneCalled() {
        whenever(accountService.findOne(1L)).thenReturn(Mono.just(Account.ACCOUNTS[0]))
        val account: Mono<Account> = accountController.getAccount(1L)
        assertEquals("Savings Account", account.block()?.name)
        verify(accountService).findOne(1L)
    }

    @Test
    fun whenGetUnknownIndividualAccount_thenReactiveServiceFindOneCalledAndExceptionThrown() {
        whenever(accountService.findOne(200L)).thenThrow(ResourceNotFoundException())
        assertThrows(ResourceNotFoundException::class.java) {
            accountController.getAccount(200L)
        }

        verify(accountService).findOne(200L)
    }

    @Test
    fun whenPostNewAccount_thenCreateCalled() {
        whenever(accountService.create(any())).thenReturn(Mono.just(Account.getCreditAccount(4L)))
        val account = accountController.createAccount(Account.getCreditAccount(null)).block()
        Assertions.assertNotEquals(null, account?.id)
        assertEquals("My Platinum Reward VISA", account?.name)
        verify(accountService).create(any())
    }
}