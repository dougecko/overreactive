package com.shinesolutions.poc.overreactive.offers.model

data class Offer(
        val id: Long,
        val type: String,
        val name: String
) {

    companion object {
        val OFFERS = arrayListOf(
                Offer(id = 1L, type = "HOME_LOAN", name = "Home Loan with 3.8% & 100,000 reward points"),
                Offer(id = 2L, type = "CREDIT_CARD", name = "Low interest credit card")
        )
    }
}