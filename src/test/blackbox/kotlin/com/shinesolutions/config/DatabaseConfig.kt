package com.shinesolutions.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import java.sql.Connection
import java.sql.DriverManager

@TestConfiguration
class DatabaseConfig(@Value("\${postgres.host}") private val host: String,
                     @Value("\${postgres.port}") private val port: Int,
                     @Value("\${postgres.database}") private val database: String,
                     @Value("\${postgres.username}") private val username: String,
                     @Value("\${postgres.password}") private val password: String) {

    @Bean
    fun connection(): Connection {
        return DriverManager.getConnection("jdbc:postgresql://$host:$port/$database", username, password)
    }
}