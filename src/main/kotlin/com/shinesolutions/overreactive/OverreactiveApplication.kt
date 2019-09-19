package com.shinesolutions.overreactive

import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.data.r2dbc.core.DatabaseClient
import reactor.core.Disposable
import reactor.core.publisher.Flux
import reactor.core.publisher.Flux.fromStream
import reactor.core.publisher.Mono
import java.net.URISyntaxException
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.Flow
import java.util.function.Consumer


@SpringBootApplication
class OverreactiveApplication

fun main(args: Array<String>) {
    runApplication<OverreactiveApplication>(*args)
}

@Bean
fun seeder(client: DatabaseClient): Disposable {
    return getSchema()
            .flatMap<Any> { sql -> executeSql(client, sql) }
            .subscribe { println("Schema created") }
}

@Throws(URISyntaxException::class)
private fun getSchema(): Mono<String> {
    val path = Paths.get(ClassLoader.getSystemResource("schema.sql").toURI())
    return Flux
            .using({ Files.lines(path) }, Function<D, Flow.Publisher<out T>> { fromStream() }, Consumer<D> { it.close() })
            .reduce({ line1, line2 -> line1 + "\n" + line2 })
}

private fun executeSql(client: DatabaseClient, sql: String): Mono<Int> {
    return client.execute().sql(sql).fetch().rowsUpdated()
}