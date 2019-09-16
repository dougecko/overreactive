package com.shinesolutions.poc.overreactive.cards.controller

import com.shinesolutions.poc.overreactive.cards.model.Card
import com.shinesolutions.poc.overreactive.cards.service.CardService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@RestController
class CardController(@Autowired val cardService: CardService) {

    @GetMapping("/cards")
    fun getCardList(): Flux<List<Card>> = cardService.findAll()

    @GetMapping("/cards/{id}")
    fun getCard(@PathVariable("id") id: Long): Mono<Card> = cardService.findOne(id)

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/cards")
    fun createCard(@RequestBody card: Card): Mono<Card> = cardService.create(card)

    @PutMapping("/cards/{id}")
    fun updateCard(@PathVariable("id") id: Long, @RequestBody card: Card): Mono<Card> = cardService.update(id, card)
}