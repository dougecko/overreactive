package com.shinesolutions.overreactive.accounts.repository

import com.shinesolutions.overreactive.accounts.model.Account
import org.reactivestreams.Publisher
import org.springframework.data.r2dbc.repository.R2dbcRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Repository
class AccountRepository : R2dbcRepository<Account, Int> {

    override fun <S : Account?> save(entity: S): Mono<S> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAll(): Flux<Account> {


        return Flux.fromIterable(Account.ACCOUNTS)
//        return Flux.fromIterable(ArrayList<Account>())
    }

    override fun deleteById(id: Int): Mono<Void> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteById(id: Publisher<Int>): Mono<Void> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAll(entities: MutableIterable<Account>): Mono<Void> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAll(entityStream: Publisher<out Account>): Mono<Void> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAll(): Mono<Void> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <S : Account?> saveAll(entities: MutableIterable<S>): Flux<S> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <S : Account?> saveAll(entityStream: Publisher<S>): Flux<S> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun count(): Mono<Long> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAllById(ids: MutableIterable<Int>): Flux<Account> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAllById(idStream: Publisher<Int>): Flux<Account> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun existsById(id: Int): Mono<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun existsById(id: Publisher<Int>): Mono<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findById(id: Int): Mono<Account> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findById(id: Publisher<Int>): Mono<Account> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(entity: Account): Mono<Void> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}