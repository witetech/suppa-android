package ai.suppa.presentation

import ai.suppa.presentation.core.StateDelegate
import ai.suppa.presentation.core.StateViewModel
import androidx.lifecycle.ViewModel

internal class ChatViewModel(
    private val stateDelegate: StateDelegate<State> = StateDelegate(),
) : ViewModel(), StateViewModel<ChatViewModel.State> by stateDelegate {
    init {
        stateDelegate.setDefaultState(State.Loading)
    }

    sealed interface State {
        data object Loading : State

        data class Error(val message: String) : State

        data class Content(val a: String) : State
    }
}
