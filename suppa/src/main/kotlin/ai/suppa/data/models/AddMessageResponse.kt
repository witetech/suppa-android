package ai.suppa.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class AddMessageResponse(
    @SerialName("output")
    val output: List<Output?>?,
) {
    @Serializable
    data class Output(
        @SerialName("content")
        val content: String? = null,
        @SerialName("type")
        val type: String? = null,
        @SerialName("value")
        val value: String? = null,
    )
}
