package ai.suppa.domain.models

internal data class Theme(
    val colorHex: String,
    val textColorHex: String,
    val iconColorHex: String,
    val imageBase64: String?,
    val removeBranding: Boolean,
)
