package ai.suppa.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AddMessageRequest(
    @SerialName("content")
    val content: String?,
    @SerialName("type")
    val type: String?,
)
