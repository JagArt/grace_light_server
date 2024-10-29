package example.com.plugins

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.cors.routing.*

fun Application.configureCors() {
    install(CORS) {
        allowHeaders { true }
        HttpMethod.DefaultMethods.forEach { allowMethod(it) }
        allowNonSimpleContentTypes = true
        anyHost()
    }
}
