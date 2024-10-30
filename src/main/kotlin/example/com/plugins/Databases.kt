package example.com.plugins

import example.com.fromModel
import example.com.model.SpeakerDto
import example.com.toModel
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import org.jetbrains.exposed.sql.Database


fun Application.configureDatabases() {
    val database = Database.connect(
        url = environment.config.property("database.url").getString(),
        user = environment.config.property("database.user").getString(),
        driver = environment.config.property("database.driver").getString(),
        password = environment.config.property("database.password").getString()
    )
    val speakersService = SpeakersService(database)
    routing {
        route(API_URL){

            //create user
            post("/v3/speakers") {
                val speakerEntity = call.receive<SpeakerDto>().fromModel()
                val id = speakersService.create(speakerEntity)
                call.respond(HttpStatusCode.OK, id)
            }

            //get user
            get("/v3/speakers/{id}") {
                val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid id")
                val speakerEntity = speakersService.read(id)
                if (speakerEntity != null) {
                    call.respond(HttpStatusCode.OK, speakerEntity.toModel())
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }

            //update user
            put("/v3/speakers/{id}") {
                val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid id")
                val speakerEntity = call.receive<SpeakerDto>().fromModel()
                speakersService.update(id, speakerEntity)
                call.respond(HttpStatusCode.OK)
            }

            delete("/v3/speakers/{id}") {
                val id = call.parameters["id"]?.toInt() ?: throw IllegalArgumentException("Invalid id")
                speakersService.delete(id)
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}