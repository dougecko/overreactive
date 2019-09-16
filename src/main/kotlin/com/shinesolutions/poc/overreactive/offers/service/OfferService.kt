package com.shinesolutions.poc.overreactive.offers.service

import com.shinesolutions.poc.overreactive.exceptions.ResourceNotFoundException
import com.shinesolutions.poc.overreactive.offers.model.Offer
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class OfferService {

    fun findAll(): Flux<List<Offer>> = Flux.just(Offer.OFFERS)

    fun findOne(id: Long) = Mono.just(Offer.OFFERS.firstOrNull { offer ->
        offer.id == id
    } ?: throw ResourceNotFoundException())
}