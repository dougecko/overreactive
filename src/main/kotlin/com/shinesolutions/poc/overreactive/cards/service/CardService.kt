package com.shinesolutions.poc.overreactive.cards.service

import com.shinesolutions.poc.overreactive.cards.model.Card
import com.shinesolutions.poc.overreactive.exceptions.ResourceNotFoundException
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import reactor.core.publisher.Mono

@Service
class CardService {

    fun findAll(): Flux<List<Card>> = Flux.just(Card.CARDS)

    fun findOne(id: Long) = Mono.just(Card.CARDS.firstOrNull { card ->
        card.id == id
    } ?: throw ResourceNotFoundException())

    fun filter(accountId: Long) = Mono.just(Card.CARDS.filter { card ->
        card.accountId == accountId
    })

    private fun findIndex(id: Long) = Card.CARDS.indexOfFirst { card ->
        card.id == id
    }

    fun create(card: Card): Mono<Card> {
        val newId = (Card.CARDS.size.toLong() + 1L)
        val newCard = Card(id = newId, accountId = card.accountId, imageUrl = card.imageUrl, number = card.number, capabilities = card.capabilities, transactionControls = card.transactionControls)
        Card.CARDS.add(newCard)
        return Mono.just(newCard)
    }

    fun update(id: Long, card: Card): Mono<Card> {
        val cardIndex = findIndex(id)
        if (cardIndex < 0) {
            throw ResourceNotFoundException()
        }
        Card.CARDS[cardIndex] = card
        return Mono.just(card)
    }

//    fun updateCardImage(id: Long, imageUrl: String): Mono<Card> {
//        val card = findOne(id)
//        card.
//                .map{c -> c.imageUrl = imageUrl}
//                .map{c -> update(id, c)}
//        return update(id, card)
//    }
}