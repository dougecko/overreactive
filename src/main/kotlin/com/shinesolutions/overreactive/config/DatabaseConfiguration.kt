package com.shinesolutions.overreactive.config

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.Connection
import io.r2dbc.spi.ConnectionFactory
import org.reactivestreams.Publisher
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.function.DatabaseClient
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories

@Configuration
@EnableR2dbcRepositories
class DatabaseConfiguration(
        @Value("\${postgres.host}") private val host: String,
        @Value("\${postgres.port}") private val port: Int,
        @Value("\${postgres.database}") private val database: String,
        @Value("\${postgres.username}") private val username: String,
        @Value("\${postgres.password}") private val password: String) : AbstractR2dbcConfiguration() {

    @Bean
    fun databaseClient(): DatabaseClient {
        return DatabaseClient.create(connectionFactory())
    }

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
    fun getConnection(): Publisher<out Connection> {
        return connectionFactory().create()
    }
}