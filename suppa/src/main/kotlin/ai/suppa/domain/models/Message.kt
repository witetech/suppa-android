package ai.suppa.domain.models

import java.util.Date

internal data class Message(
    val content: MessageContent,
    val createdAt: Date,
    val id: String,
    val role: Role,
) {
    enum class Role { USER, ASSISTANT, UNKNOWN }
}
