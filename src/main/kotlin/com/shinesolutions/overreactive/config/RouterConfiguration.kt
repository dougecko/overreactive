package com.shinesolutions.overreactive.config

import com.shinesolutions.overreactive.accounts.handler.AccountHandler
import kotlinx.coroutines.FlowPreview
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class RouterConfiguration {

    @FlowPreview
    @Bean
    fun productRoutes(accountHandler: AccountHandler) = coRouter {
        GET("/", accountHandler::findAll)
    }
}