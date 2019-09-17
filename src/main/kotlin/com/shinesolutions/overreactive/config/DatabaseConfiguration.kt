package com.shinesolutions.overreactive.config

import com.shinesolutions.overreactive.accounts.repository.AccountRepository
import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.core.DatabaseClient
import org.springframework.data.r2dbc.core.DefaultReactiveDataAccessStrategy
import org.springframework.data.r2dbc.dialect.PostgresDialect
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
import org.springframework.data.r2dbc.repository.support.R2dbcRepositoryFactory
import org.springframework.data.relational.core.mapping.RelationalMappingContext

@Configuration
@EnableR2dbcRepositories
class DatabaseConfiguration(
        @Value("\${spring.data.postgres.host}") private val host: String,
        @Value("\${spring.data.postgres.database}") private val database: String,
        @Value("\${spring.data.postgres.username}") private val username: String,
        @Value("\${spring.data.postgres.password}") private val password: String
) : AbstractR2dbcConfiguration() {

    @Bean
    fun accountRepository(): AccountRepository {
        return r2dbcRepositoryFactory().getRepository(AccountRepository::class.java)
    }

    @Bean
    fun r2dbcRepositoryFactory(): R2dbcRepositoryFactory {
        val context = RelationalMappingContext()
        context.afterPropertiesSet()
        return R2dbcRepositoryFactory(databaseClient(), DefaultReactiveDataAccessStrategy(PostgresDialect()))
    }

    @Bean
    fun databaseClient(): DatabaseClient {
        return DatabaseClient.create(connectionFactory())
    }

    override fun connectionFactory(): ConnectionFactory {
        return PostgresqlConnectionFactory(
                PostgresqlConnectionConfiguration.builder()
                        .host(host)
                        .database(database)
                        .username(username)
                        .password(password)
                        .build()
        )
    }

}