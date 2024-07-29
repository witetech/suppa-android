package ai.suppa.presentation

import ai.suppa.presentation.ChatViewModel.State
import ai.suppa.presentation.theme.SuppaTheme
import ai.suppa.presentation.widgets.*
import ai.suppa.presentation.widgets.ErrorScaffold
import ai.suppa.presentation.widgets.LoadingScaffold
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
                when (state) {
                    is State.Loading -> LoadingScaffold()
                    is State.Error -> ErrorScaffold(state = state as State.Error)
                    is State.Content -> ContentScaffold(content = state as State.Content)
                }
            }
        }
    }

    companion object {
        fun newIntent(activity: ComponentActivity): Intent {
            return Intent(activity, ChatActivity::class.java)
        }
    }
}
