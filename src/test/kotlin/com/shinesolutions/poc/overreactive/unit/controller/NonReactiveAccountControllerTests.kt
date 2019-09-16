package com.shinesolutions.poc.overreactive.unit.controller

import com.nhaarman.mockitokotlin2.whenever
import com.shinesolutions.poc.overreactive.accounts.model.Account
import com.shinesolutions.poc.overreactive.accounts.service.NonReactiveAccountService
import com.shinesolutions.poc.overreactive.exceptions.ResourceNotFoundException
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

@ExtendWith(SpringExtension::class)
@SpringBootTest
class NonReactiveAccountControllerTests {

    @Mock
    lateinit var accountService: NonReactiveAccountService

    @Autowired
    lateinit var accountController: NonReactiveAccountController

    @BeforeEach
    fun init() {
        accountController = NonReactiveAccountController(accountService)
    }

    @Test
    fun whenGetAllAccounts_thenNonReactiveServiceFindAllCalled() {
        whenever(accountService.findAll()).thenReturn(Account.ACCOUNTS)
        val accounts: List<Account> = accountController.getAccountsList()
        assertEquals(3, accounts.size)

        verify(accountService).findAll()
    }

    @Test
    fun whenGetIndividualAccount_thenNonReactiveServiceFindOneCalled() {
        whenever(accountService.findOne(1L)).thenReturn(Account.ACCOUNTS[0])
        val account: Account = accountController.getAccount(1L)
        assertEquals("Savings Account", account.name)
        verify(accountService).findOne(1L)
    }

    @Test
    fun whenGetUnknownIndividualAccount_thenNonReactiveServiceFindOneCalledAndExceptionThrown() {
        whenever(accountService.findOne(200L)).thenThrow(ResourceNotFoundException())
        assertThrows(ResourceNotFoundException::class.java) {
            accountController.getAccount(200L)
        }

        verify(accountService).findOne(200L)
    }
}