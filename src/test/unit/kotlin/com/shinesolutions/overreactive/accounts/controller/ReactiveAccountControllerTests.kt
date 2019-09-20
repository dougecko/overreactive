package com.shinesolutions.overreactive.accounts.controller

import com.shinesolutions.overreactive.accounts.repository.ReactiveAccountRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mock
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension

@ExtendWith(SpringExtension::class)
@SpringBootTest
class ReactiveAccountControllerTests {

    @Mock
    lateinit var accountRepository: ReactiveAccountRepository

    @Autowired
    lateinit var accountController: ReactiveAccountController

    @BeforeEach
    fun init() {
        accountController = ReactiveAccountController(accountRepository)
//        Account.resetAccounts()
    }

//    @Test
//    fun whenGetAllAccounts_thenReactiveServiceFindAllCalled() {
//        whenever(accountService.findAll()).thenReturn(Flux.fromIterable(Account.ACCOUNTS))
//        val accounts: Flux<Account> = accountController.getAccountsList()
//
//        verify(accountService).findAll()
//        assertEquals(3, accounts.collectList().block()?.size)
//    }
//
//    @Test
//    fun whenGetIndividualAccount_thenReactiveServiceFindOneCalled() {
//        whenever(accountService.findOne(1L)).thenReturn(Mono.just(Account.ACCOUNTS[0]))
//        val account: Mono<Account> = accountController.getAccount(1L)
//
//        verify(accountService).findOne(1L)
//        assertEquals("Savings Account", account.block()?.name)
//    }

//    @Test
//    fun whenGetUnknownIndividualAccount_thenReactiveServiceFindOneCalledAndExceptionThrown() {
//        whenever(accountService.findOne(200L)).thenThrow(ResourceNotFoundException())
//        assertThrows(ResourceNotFoundException::class.java) {
//            accountController.getAccount(200L)
//        }
//
//        verify(accountService).findOne(200L)
//    }

//    @Test
//    fun whenPostNewAccount_thenCreateCalled() {
//        whenever(accountService.create(any())).thenReturn(Mono.just(Account.getCreditAccount(4L)))
//        val account = accountController.createAccount(Account.getCreditAccount(null)).block()
//        Assertions.assertNotEquals(null, account?.id)
//        assertEquals("My Platinum Reward VISA", account?.name)
//        verify(accountService).create(any())
//    }
}