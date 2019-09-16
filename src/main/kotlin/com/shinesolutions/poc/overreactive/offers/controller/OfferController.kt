package com.shinesolutions.poc.overreactive.offers.controller

import com.shinesolutions.poc.overreactive.offers.model.Offer
import com.shinesolutions.poc.overreactive.offers.service.OfferService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class OfferController(@Autowired val offerService: OfferService) {

    @GetMapping("/offers")
    fun getOffersList(): Flux<List<Offer>> = offerService.findAll()

    @GetMapping("/offers/{id}")
    fun getOffer(@PathVariable("id") id: Long): Mono<Offer> = offerService.findOne(id)

}