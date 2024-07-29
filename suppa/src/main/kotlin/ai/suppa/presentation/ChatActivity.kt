package ai.suppa.presentation

import ai.suppa.presentation.ChatViewModel.State
import ai.suppa.presentation.theme.SuppaTheme
import ai.suppa.presentation.widgets.*
import android.content.Intent
import android.os.Bundle
import androidx.activity.*
import androidx.activity.compose.setContent
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

internal class ChatActivity : ComponentActivity() {
    private val viewModel by viewModels<ChatViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val apiKey = intent.getStringExtra(API_KEY) ?: throw IllegalArgumentException()
        lifecycleScope.launch { viewModel.createChat(apiKey) }
        setContent {
            SuppaTheme {
                val state by viewModel.state.collectAsStateWithLifecycle()
                when (state) {
                    is State.Loading -> LoadingScaffold()
                    is State.Error -> ErrorScaffold(state = state as State.Error)
                    is State.Content -> ContentScaffold(content = state as State.Content)
                }
            }
        }
    }

    companion object {
        private const val API_KEY = "apiKey"

        fun newIntent(
            activity: ComponentActivity,
            apiKey: String,
        ): Intent {
            return Intent(activity, ChatActivity::class.java).apply {
                putExtra(API_KEY, apiKey)
            }
        }
    }
}
