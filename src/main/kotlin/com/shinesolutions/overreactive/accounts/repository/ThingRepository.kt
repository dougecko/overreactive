package com.shinesolutions.overreactive.accounts.repository

import com.shinesolutions.overreactive.accounts.model.Thing
import org.springframework.data.r2dbc.repository.query.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Flux

interface ThingRepository: ReactiveCrudRepository<Thing, Int> {

    @Query("SELECT * FROM thing")
    override fun findAll(): Flux<Thing>

}