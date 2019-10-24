import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.gradle.api.tasks.testing.logging.TestLogEvent
import org.jetbrains.kotlin.gradle.plugin.KotlinSourceSet
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

// Need to go pre-release for reactive transaction support
val kotlinVersion = "1.3.50"
val r2dbcVersion = "1.0.0.M1"
val r2dbcSpiVersion = "1.0.0.M7"
val r2dbcPostgresVersion = "1.0.0.M7"
val springBootVersion = "2.2.0.M6"
val ktCoroutinesVersion = "1.2.2"
val junitVersion = "5.5.2"

plugins {
    id("org.springframework.boot") version "2.2.0.M6"
    id("io.spring.dependency-management") version "1.0.8.RELEASE"
    kotlin("jvm") version "1.3.50"
    kotlin("plugin.spring") version "1.3.50"
}

group = "com.shinesolutions"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_1_8

val developmentOnly by configurations.creating
configurations {
    runtimeClasspath {
        extendsFrom(developmentOnly)
    }
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    implementation("org.springframework.data:spring-data-r2dbc:$r2dbcVersion")

    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("io.r2dbc:r2dbc-postgresql:$r2dbcPostgresVersion")
    implementation("io.r2dbc:r2dbc-spi:$r2dbcSpiVersion")
    implementation("io.r2dbc:r2dbc-client:$r2dbcSpiVersion")
    implementation("io.r2dbc:r2dbc-pool:0.8.0.M8")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$ktCoroutinesVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$ktCoroutinesVersion")
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    testImplementation("org.springframework.boot:spring-boot-starter-test:$springBootVersion") {
        exclude(group = "junit")
        exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
    }
    testImplementation("org.junit.jupiter:junit-jupiter-api")
    testImplementation("org.junit.jupiter:junit-jupiter-engine")
    testImplementation("io.projectreactor:reactor-test")
    testImplementation("com.nhaarman.mockitokotlin2:mockito-kotlin:2.2.0")
    testImplementation("org.postgresql:postgresql:42.2.8")
}

dependencyManagement {
    imports {
        mavenBom("org.junit:junit-bom:$junitVersion")
    }
}

sourceSets["test"].withConvention(KotlinSourceSet::class) {
    kotlin.srcDirs("src/test/unit/kotlin", "src/test/integration/kotlin", "src/test/blackbox/kotlin")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "1.8"
    }
}

tasks.test {
    useJUnitPlatform {
        includeEngines("junit-jupiter")
    }

    testLogging {
        events = mutableSetOf(
                TestLogEvent.FAILED,
                TestLogEvent.PASSED,
                TestLogEvent.SKIPPED
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

        afterSuite(KotlinClosure2<TestDescriptor, TestResult, Unit>({ desc, result ->
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
