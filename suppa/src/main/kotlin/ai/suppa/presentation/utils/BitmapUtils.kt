package ai.suppa.presentation.utils

import android.graphics.*
import android.util.Base64

internal object BitmapUtils {
    private fun convertBase64ImageToBitmap(imageBase64: String): Bitmap? {
        val substring = imageBase64.substringAfter("base64,")
        val decodedString: ByteArray = Base64.decode(substring, Base64.DEFAULT)
        return try {
            BitmapFactory.decodeByteArray(decodedString, 0, decodedString.size)
        } catch (e: Exception) {
            null
        }
    }
}
