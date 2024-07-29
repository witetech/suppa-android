package ai.suppa.data.models

import kotlinx.serialization.*

@Serializable
internal data class GetChatResponse(
    @SerialName("color")
    val color: String?,
    @SerialName("iconColor")
    val iconColor: String?,
    @SerialName("image")
    val image: String?,
    @SerialName("messages")
    val messages: List<Message?>?,
    @SerialName("name")
    val name: String?,
    @SerialName("removeBranding")
    val removeBranding: String?,
    @SerialName("textColor")
    val textColor: String?,
) {
    @Serializable
    data class Message(
        @SerialName("aiGenerated")
        val aiGenerated: Boolean?,
        @SerialName("canvas")
        val canvas: String?,
        @SerialName("chatId")
        val chatId: String?,
        @SerialName("chatbotId")
        val chatbotId: String?,
        @SerialName("content")
        val content: String?,
        @SerialName("createdAt")
        val createdAt: String?,
        @SerialName("id")
        val id: String?,
        @SerialName("language")
        val language: String?,
        @SerialName("nodeId")
        val nodeId: String?,
        @SerialName("role")
        val role: String?,
        @SerialName("sentiment")
        val sentiment: Int?,
        @SerialName("testing")
        val testing: Boolean?,
        @SerialName("userId")
        val userId: String?,
    )
}
