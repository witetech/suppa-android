package ai.suppa

import ai.suppa.presentation.ChatActivity
import androidx.activity.ComponentActivity

fun ComponentActivity.startChat() {
    ChatActivity.newIntent(this).also {
        startActivity(it)
    }
}
