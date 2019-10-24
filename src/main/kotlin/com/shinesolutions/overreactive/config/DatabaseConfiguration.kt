package com.shinesolutions.overreactive.config

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import java.sql.Connection
import java.sql.DriverManager

@Configuration
@EnableR2dbcRepositories("com.shinesolutions.overreactive.accounts.repository")
class DatabaseConfiguration(
        @Value("\${postgres.host}") private val host: String,
        @Value("\${postgres.port}") private val port: Int,
        @Value("\${postgres.database}") private val database: String,
        @Value("\${postgres.username}") private val username: String,
        @Value("\${postgres.password}") private val password: String) : AbstractR2dbcConfiguration() {

    override fun connectionFactory(): ConnectionFactory {
        return PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host(host)
                        .port(port)
                        .database(database)
                        .username(username)
                        .password(password)
                        .build()
        )
    }

    @Bean
    fun connection(): Connection {
        return DriverManager.getConnection("jdbc:postgresql://$host:$port/$database", username, password)
    }
}