package com.shinesolutions.overreactive.offers.service

import com.shinesolutions.overreactive.exceptions.ResourceNotFoundException
import com.shinesolutions.overreactive.offers.model.Offer
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class OfferService {

    fun findAll(): Flux<Offer> = Flux.fromIterable(Offer.OFFERS)

    fun findOne(id: Long) = Mono.just(Offer.OFFERS.firstOrNull { offer ->
        offer.id == id
    } ?: throw ResourceNotFoundException())
}