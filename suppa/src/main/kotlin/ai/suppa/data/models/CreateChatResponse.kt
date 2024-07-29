package ai.suppa.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CreateChatResponse(
    @SerialName("chatId")
    val chatId: String?,
)
