
import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import java.net.URI

plugins {
    id("org.springframework.boot") version "2.1.8.RELEASE"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    java
    kotlin("jvm") version "1.3.50"
    kotlin("plugin.spring") version "1.3.50"
}

group = "com.shinesolutions.poc"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

repositories {
    mavenCentral()
    maven { url = URI("http://repo.spring.io/libs-milestone") }
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.data:spring-data-r2dbc:1.0.0.M2")
    implementation("io.r2dbc:r2dbc-postgresql:1.0.0.M7")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")

    testImplementation("org.springframework.boot:spring-boot-starter-test") {
        exclude(module = "junit")
    }
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
    testImplementation("org.mockito:mockito-junit-jupiter:3.0.0")
    testImplementation("io.projectreactor:reactor-test")
}

sourceSets["test"].withConvention(KotlinSourceSet::class) {
    kotlin.srcDirs("src/test/unit/kotlin", "src/test/integration/kotlin")
}

tasks.test {
    useJUnitPlatform {
        includeEngines("junit-jupiter")
    }

    testLogging {
        events = mutableSetOf(
                TestLogEvent.FAILED,
                TestLogEvent.PASSED,
                TestLogEvent.SKIPPED//,
        //        TestLogEvent.STANDARD_OUT
        )
        exceptionFormat = TestExceptionFormat.FULL
        showExceptions = true
        showCauses = true
        showStackTraces = true

        debug {
            events = mutableSetOf(
                    TestLogEvent.STARTED,
                    TestLogEvent.FAILED,
                    TestLogEvent.PASSED,
                    TestLogEvent.SKIPPED,
                    TestLogEvent.STANDARD_ERROR,
                    TestLogEvent.STANDARD_OUT)
            exceptionFormat = TestExceptionFormat.FULL
        }

        info.events = debug.events
        info.exceptionFormat = debug.exceptionFormat

        afterSuite(KotlinClosure2<TestDescriptor, TestResult, Unit>({desc, result ->
            if (desc.parent == null) { // will match the outermost suite
                val output = "Results: ${result.resultType} (${result.testCount} tests, ${result.successfulTestCount} successes, ${result.failedTestCount} failures, ${result.skippedTestCount} skipped)"
                val startItem = "|  "
                val endItem = "  |"
                val repeatLength = startItem.length + output.length + endItem.length
                println('\n' + "-".repeat(repeatLength) + '\n' + startItem + output + endItem + '\n' + "-".repeat(repeatLength))
            }
        }))
    }
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}
