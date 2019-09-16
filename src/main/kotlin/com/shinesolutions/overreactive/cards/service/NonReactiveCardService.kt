package com.shinesolutions.overreactive.cards.service

import com.shinesolutions.overreactive.cards.model.Card
import com.shinesolutions.overreactive.exceptions.ResourceNotFoundException
import org.springframework.stereotype.Service

@Service
class NonReactiveCardService {
    fun findAll(): List<Card> = Card.CARDS

    fun findOne(id: Long) = Card.CARDS.firstOrNull { card ->
        card.id == id
    } ?: throw ResourceNotFoundException()

    fun filter(accountId: Long) = Card.CARDS.filter { card ->
        card.accountId == accountId
    }

    private fun findIndex(id: Long) = Card.CARDS.indexOfFirst { card ->
        card.id == id
    }

    fun create(card: Card): Card {
        val newId = (Card.CARDS.size.toLong() + 1L)
        val newCard = Card(id = newId, accountId = card.accountId, imageUrl = card.imageUrl, number = card.number, capabilities = card.capabilities, transactionControls = card.transactionControls)
        Card.CARDS.add(newCard)
        return newCard
    }

    fun update(id: Long, card: Card): Card {
        val cardIndex = findIndex(id)
        if (cardIndex < 0) {
            throw ResourceNotFoundException()
        }
        Card.CARDS[cardIndex] = card
        return card
    }

    fun updateCardImage(id: Long, imageUrl: String): Card {
        val card = findOne(id)
        card.imageUrl = imageUrl
        return update(id, card)
    }
}