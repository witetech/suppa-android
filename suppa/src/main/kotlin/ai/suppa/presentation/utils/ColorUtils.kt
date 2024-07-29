package ai.suppa.presentation.utils

import androidx.compose.ui.graphics.Color

object ColorUtils {
    fun hexToColor(hex: String): Color {
        return Color(android.graphics.Color.parseColor("#$hex"))
    }
}
