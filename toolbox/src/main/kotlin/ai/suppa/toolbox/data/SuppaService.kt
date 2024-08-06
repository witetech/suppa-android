package ai.suppa.toolbox.data

import ai.suppa.toolbox.data.models.GetChatbotsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val BASE_URL = "https://app.suppa.ai/api"
private const val API_KEY = "027dbdca-ff82-41fb-8f26-089cf261cc48"
private const val TIMEOUT = 30_000

class SuppaService {
    private val client = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    ignoreUnknownKeys = true
                },
            )
        }

        install(Logging) {
            logger = Logger.ANDROID
            level = LogLevel.ALL
        }

        engine {
            connectTimeout = TIMEOUT
            socketTimeout = TIMEOUT
        }
    }

    suspend fun getChatbots(): GetChatbotsResponse {
        return client.get("$BASE_URL/chatbot") {
            headers {
                append("Authorization", "apikey $API_KEY")
            }
        }.body<GetChatbotsResponse>()
    }
}
