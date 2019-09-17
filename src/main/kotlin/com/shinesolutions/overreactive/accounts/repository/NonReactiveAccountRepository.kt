package com.shinesolutions.overreactive.accounts.repository

import com.shinesolutions.overreactive.accounts.model.Account
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
class NonReactiveAccountRepository : CrudRepository<Account, Int> {
    override fun <S : Account?> save(entity: S): S {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAll(): MutableIterable<Account> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteById(id: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAll(entities: MutableIterable<Account>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun deleteAll() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun <S : Account?> saveAll(entities: MutableIterable<S>): MutableIterable<S> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun count(): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findAllById(ids: MutableIterable<Int>): MutableIterable<Account> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun existsById(id: Int): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun findById(id: Int): Optional<Account> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun delete(entity: Account) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}