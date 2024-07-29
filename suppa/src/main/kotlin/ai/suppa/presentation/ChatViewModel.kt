package ai.suppa.presentation

import ai.suppa.domain.models.*
import ai.suppa.domain.models.Theme
import ai.suppa.presentation.core.StateDelegate
import ai.suppa.presentation.core.StateViewModel
import androidx.lifecycle.ViewModel

internal class ChatViewModel(
    private val stateDelegate: StateDelegate<State> = StateDelegate(),
) : ViewModel(), StateViewModel<ChatViewModel.State> by stateDelegate {
    init {
        stateDelegate.setDefaultState(
            State.Content(
                config = Config(
                    chatId = "",
                    name = "Suppa",
                    description = "Suppa is a chat application",
                    apiKey = "",
                ),
                theme = Theme(
                    colorHex = "000000",
                    iconColorHex = "FFFFFF",
                    textColorHex = "FFFFFF",
                    imageBase64 = null,
                    removeBranding = false,
                ),
            ),
        )
    }

    sealed interface State {
        data object Loading : State

        data class Error(val message: String) : State

        data class Content(val config: Config, val theme: Theme) : State
    }
}
