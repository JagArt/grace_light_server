package example.com.plugins

import example.com.model.SpeakerDto
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.statuspages.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

const val API_URL = "/demo/api"

fun Application.configureRouting() {
    install(StatusPages) {
        status(HttpStatusCode.NotFound) { call, _ ->
            call.respondText(text = "Not Found", status = HttpStatusCode.NotFound)
        }
        exception<Throwable> { call, cause ->
            call.respondText(text = "500: $cause", status = HttpStatusCode.InternalServerError)
        }
    }
    routing {
        route(API_URL) {
            route("v2") {
                get("/speakers/{id}") {
                    call.respond(
                        SpeakerDto(
                            id = call.pathParameters["id"]?.toInt(),
                            firstName = "Johnny",
                            lastName = "Bravo",
                            age = 30,
                            description = "I am a speaker",
                        )
                    )
                }
                webSocket("/speakers") {
                    launch {
                        while (isActive) {
                            val speakerDto = receiveDeserialized<SpeakerDto>()
                            println(speakerDto)
                        }
                    }
                    while (closeReason.isActive) {
                        sendSerialized(
                            SpeakerDto(
                                id = call.parameters["id"]?.toInt(),
                                firstName = "Johnny",
                                lastName = "Bravo",
                                age = 30,
                                description = "I am a speaker",
                            )
                        )
                        delay(1000)
                    }
                }
            }
        }
        get("/") {
            call.respondText("Hello World!")
        }
    }
}
