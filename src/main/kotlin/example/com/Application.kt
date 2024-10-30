package example.com

import configureLogging
import example.com.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    configureSerialization()
    configureSecurity()
    configureSockets()
    configureCors()
    configureLogging()
    configureDatabases()
    configureRouting()
}

