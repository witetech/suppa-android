package ai.suppa.domain.models

internal data class MessageContent(
    val content: String?,
    val type: Type,
    val value: String?,
) {
    enum class Type {
        MESSAGE,
        INPUT,
        USER_MESSAGE,
        UNKNOWN,
    }
}
