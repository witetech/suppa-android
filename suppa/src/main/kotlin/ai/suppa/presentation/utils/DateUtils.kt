package ai.suppa.presentation.utils

import java.text.SimpleDateFormat
import java.util.*

internal object DateUtils {
    fun parseDate(dateString: String): Date {
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        return formatter.parse(dateString) ?: Date()
    }
}
