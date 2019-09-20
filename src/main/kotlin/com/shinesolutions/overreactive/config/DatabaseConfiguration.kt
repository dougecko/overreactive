package com.shinesolutions.overreactive.config

import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
import io.r2dbc.postgresql.PostgresqlConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
import org.springframework.data.r2dbc.function.DatabaseClient
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories


@Configuration
@EnableR2dbcRepositories
class DatabaseConfiguration
(
        @Value("\${postgres.host}") private val host: String,
        @Value("\${postgres.port}") private val port: Int,
        @Value("\${postgres.database}") private val database: String,
        @Value("\${postgres.username}") private val username: String,
        @Value("\${postgres.password}") private val password: String
) : AbstractR2dbcConfiguration() {

//    @Bean
//    fun accountRepository(): AccountRepository {
//        return r2dbcRepositoryFactory().getRepository(AccountRepository::class.java)
//    }

//    @Bean
//    fun r2dbcRepositoryFactory(): R2dbcRepositoryFactory {
//        val context = RelationalMappingContext()
//        context.afterPropertiesSet()
//        return R2dbcRepositoryFactory(databaseClient(), DefaultReactiveDataAccessStrategy(PostgresDialect()))
//    }

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

//    @Bean
//    fun dataSource(): DataSource {
//
//        val builder = EmbeddedDatabaseBuilder()
//        return builder.setType(EmbeddedDatabaseType.HSQL).build()
//    }

//    @Bean
//    fun entityManagerFactory(): EntityManagerFactory? {
//
//        val vendorAdapter = HibernateJpaVendorAdapter()
//        vendorAdapter.setGenerateDdl(true)
//
//        val factory = LocalContainerEntityManagerFactoryBean()
//        factory.jpaVendorAdapter = vendorAdapter
//        factory.setPackagesToScan("com.shinesolutions.overreactive")
//        factory.dataSource = dataSource()
//        factory.afterPropertiesSet()
//
//        return factory.getObject()
//    }
//
//    @Bean
//    fun transactionManager(): PlatformTransactionManager {
//
//        val txManager = JpaTransactionManager()
//        txManager.entityManagerFactory = entityManagerFactory()
//        return txManager
//    }
}