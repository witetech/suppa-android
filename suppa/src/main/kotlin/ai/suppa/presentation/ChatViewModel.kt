package ai.suppa.presentation

import ai.suppa.data.SuppaService
import ai.suppa.domain.models.*
import ai.suppa.domain.models.Theme
import ai.suppa.presentation.core.StateDelegate
import ai.suppa.presentation.core.StateViewModel
import androidx.lifecycle.ViewModel

internal class ChatViewModel(
    private val suppaService: SuppaService = SuppaService(),
    private val stateDelegate: StateDelegate<State> = StateDelegate(),
) : ViewModel(), StateViewModel<ChatViewModel.State> by stateDelegate {
    init {
        stateDelegate.setDefaultState(State.Loading)
    }

    suspend fun createChat(apiKey: String) {
        stateDelegate.updateState { State.Loading }
        try {
            val createChatResponse = suppaService.createChat(apiKey)
            val chatId = createChatResponse.chatId!!
        } catch (e: Exception) {
            e.printStackTrace()
            stateDelegate.updateState { State.Error("Something went wrong") }
        }
    }

    sealed interface State {
        data object Loading : State

        data class Error(val message: String) : State

        data class Content(val config: Config, val theme: Theme) : State
    }
}
