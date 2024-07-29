package ai.suppa.presentation

import ai.suppa.presentation.theme.SuppaTheme
import android.content.Intent
import android.os.Bundle
import androidx.activity.*
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle

internal class ChatActivity : ComponentActivity() {
    private val viewModel by viewModels<ChatViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuppaTheme {
                val state by viewModel.state.collectAsStateWithLifecycle()
            }
        }
    }

    companion object {
        fun newIntent(activity: ComponentActivity): Intent {
            return Intent(activity, ChatActivity::class.java)
        }
    }
}
