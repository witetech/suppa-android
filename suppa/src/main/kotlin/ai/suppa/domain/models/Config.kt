package ai.suppa.domain.models

internal data class Config(
    val chatId: String,
    val name: String,
    val description: String?,
    val apiKey: String,
)
