package com.shinesolutions.poc.overreactive.cards.controller

import com.shinesolutions.poc.overreactive.cards.model.Card
import com.shinesolutions.poc.overreactive.cards.service.CardService
import com.shinesolutions.poc.overreactive.cards.service.NonReactiveCardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class CardController(@Autowired val cardService: CardService,
                     @Autowired val nrCardService: NonReactiveCardService) {

    ///// REACTIVE ENDPOINTS
    @GetMapping("/cards")
    fun getCardList(): Flux<List<Card>> = cardService.findAll()

    @GetMapping("/cards/{id}")
    fun getCard(@PathVariable("id") id: Long): Mono<Card> = cardService.findOne(id)

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/cards")
    fun createCard(@RequestBody card: Card): Mono<Card> = cardService.create(card)

    @PutMapping("/cards/{id}")
    fun updateCard(@PathVariable("id") id: Long, @RequestBody card: Card): Mono<Card> = cardService.update(id, card)

    @DeleteMapping("/cards/{id}")
    fun deleteCard(@PathVariable("id") id: Long) = cardService.delete(id);

    ///// NON-REACTIVE ENDPOINTS
    @GetMapping("/nr/cards")
    fun getNonReactiveCardList(): List<Card> = nrCardService.findAll()

    @GetMapping("/nr/cards/{id}")
    fun getNonReactiveCard(@PathVariable("id") id: Long): Card = nrCardService.findOne(id)

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/nr/cards")
    fun createNonReactiveCard(@RequestBody card: Card): Card = nrCardService.create(card)

    @PutMapping("/nr/cards/{id}")
    fun updateNonReactiveCard(@PathVariable("id") id: Long, @RequestBody card: Card): Card = nrCardService.update(id, card)
}