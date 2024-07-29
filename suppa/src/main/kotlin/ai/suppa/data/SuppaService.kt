package ai.suppa.data

import ai.suppa.data.models.*
import ai.suppa.data.models.CreateChatResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

private const val BASE_URL = "https://app.suppa.ai/api"
private const val TIMEOUT = 30_000

internal class SuppaService {
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

    suspend fun createChat(apiKey: String): CreateChatResponse {
        return client.post("$BASE_URL/chat") {
            headers {
                append("x-platform", "android")
                append("x-api-key", apiKey)
            }
        }.body<CreateChatResponse>()
    }

    suspend fun getChat(
        apiKey: String,
        chatId: String,
    ): GetChatResponse {
        return client.get("$BASE_URL/chat/$chatId") {
            headers {
                append("x-platform", "android")
                append("x-api-key", apiKey)
            }
        }.body<GetChatResponse>()
    }

    suspend fun addMessage(
        apiKey: String,
        chatId: String,
        addMessageRequest: AddMessageRequest,
    ): AddMessageResponse {
        return client.post("$BASE_URL/chat/$chatId/message") {
            headers {
                append("x-platform", "android")
                append("x-api-key", apiKey)
            }
            contentType(ContentType.Application.Json)
            setBody(addMessageRequest)
        }.body<AddMessageResponse>()
    }
}
