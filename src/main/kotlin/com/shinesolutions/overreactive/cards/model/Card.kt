package com.shinesolutions.overreactive.cards.model

data class Card(
        val id: Long,
        val accountId: Long,
        val number: String,
        var imageUrl: String,
        var transactionControls: TransactionControls,
        var capabilities: Capabilities
) {

    companion object {
        val CARDS = arrayListOf(
                Card(id = 1L, accountId = 1L, number = "123456XXXXXX1234", imageUrl = "https://BLAH.com.au/card-debit.jpg",
                        transactionControls = TransactionControls(overseas = true, contactless = false, online = true),
                        capabilities = Capabilities(activation = true, nabpay = true)),

                Card(id = 2L, accountId = 2L, number = "111111xxxxxx1111", imageUrl = "https://BLAH.com.au/card-credit-platinum.jpg",
                        transactionControls = TransactionControls(overseas = false, contactless = true, online = true),
                        capabilities = Capabilities(activation = false, nabpay = true)),

                Card(id = 3L, accountId = 3L, number = "222222xxxxxx2222", imageUrl = "https://BLAH.com.au/card-credit-platinum.jpg",
                        transactionControls = TransactionControls(overseas = true, contactless = true, online = true),
                        capabilities = Capabilities(activation = false, nabpay = false))
        )
    }

}