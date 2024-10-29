package example.com

import configureLogging
import example.com.plugins.configureCors
import example.com.plugins.configureRouting
import example.com.plugins.configureSecurity
import example.com.plugins.configureSerialization
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureSecurity()
    configureRouting()
    configureCors()
    configureLogging()
}

