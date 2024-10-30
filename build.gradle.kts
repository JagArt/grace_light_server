plugins {
    kotlin("jvm")
    id("io.ktor.plugin")
    id("com.github.johnrengelman.shadow")
    kotlin("plugin.serialization")
}

group = "example.com"
version = "0.0.1"

application {
    mainClass.set("io.ktor.server.netty.EngineMain")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.core.jvm)
    implementation(libs.ktor.server.content.negotiation.jvm)
    implementation(libs.ktor.server.auth.jvm)
    implementation(libs.ktor.server.netty.jvm)
    implementation(libs.logback.classic)
    implementation(libs.ktor.server.config.yaml)
    testImplementation(libs.ktor.server.test.host.jvm)
    testImplementation(Kotlin.test.junit)

    implementation(Ktor.server.statusPages)
    implementation(Ktor.server.cors)
    implementation(Ktor.server.callLogging)
    implementation(Ktor.plugins.serialization.kotlinx.json)
    implementation(Ktor.server.websockets)
    implementation(JetBrains.exposed.core)
    implementation(JetBrains.exposed.dao)
    implementation(JetBrains.exposed.jdbc)
    implementation(libs.h2)
}

tasks {
    create("stage").dependsOn("installDist")
}
