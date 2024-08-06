package ai.suppa.toolbox.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GetChatbotsResponse(
    @SerialName("chatbots")
    val chatbots: List<Chatbot?>? = null,
) {
    @Serializable
    data class Chatbot(
        @SerialName("canvas")
        val canvas: String? = null,
        @SerialName("color")
        val color: String? = null,
        @SerialName("companyDescription")
        val companyDescription: String? = null,
        @SerialName("companyName")
        val companyName: String? = null,
        @SerialName("createdAt")
        val createdAt: String? = null,
        @SerialName("deployedCanvas")
        val deployedCanvas: String? = null,
        @SerialName("iconColor")
        val iconColor: String? = null,
        @SerialName("id")
        val id: String? = null,
        @SerialName("image")
        val image: String? = null,
        @SerialName("name")
        val name: String? = null,
        @SerialName("removeBranding")
        val removeBranding: String? = null,
        @SerialName("status")
        val status: String? = null,
        @SerialName("textColor")
        val textColor: String? = null,
    )
}
