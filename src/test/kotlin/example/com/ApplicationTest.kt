package example.com

import example.com.model.SpeakerDto
import example.com.plugins.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import kotlin.test.*

class ApplicationTest {
    @Test
    fun testRoot() = testApplication {
        environment {
            config = ApplicationConfig("application-test.yaml")
        }
        application {
            module()
        }
        val client = createClient {
            install(ContentNegotiation){
                json()
            }
        }
        client.get("$API_URL/v2/speakers/1").apply {
            assertEquals(HttpStatusCode.OK, status)
            val responseBody = body<SpeakerDto>()
            assertEquals(1, responseBody.id)
            assertEquals("Johnny", responseBody.firstName)
        }
    }
}
