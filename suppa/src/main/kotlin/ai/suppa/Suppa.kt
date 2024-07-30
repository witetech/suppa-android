package ai.suppa

import ai.suppa.presentation.ChatActivity
import androidx.activity.ComponentActivity

fun ComponentActivity.launchSuppa(apiKey: String) {
    ChatActivity.newIntent(this, apiKey).also {
        startActivity(it)
    }
}
