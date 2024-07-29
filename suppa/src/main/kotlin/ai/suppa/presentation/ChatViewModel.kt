package ai.suppa.presentation

import ai.suppa.data.SuppaService
import ai.suppa.data.models.AddMessageRequest
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
            suppaService.addMessage(
                apiKey,
                chatId,
                AddMessageRequest(
                    content = "open",
                    type = "open",
                ),
            )
            val getChatResponse = suppaService.getChat(apiKey, chatId)
            stateDelegate.updateState {
                State.Content(
                    config = Config(
                        chatId = chatId,
                        name = getChatResponse.name!!,
                        description = null,
                        apiKey = apiKey,
                    ),
                    theme = Theme(
                        colorHex = getChatResponse.color ?: "000000",
                        textColorHex = getChatResponse.textColor ?: "FFFFFF",
                        iconColorHex = getChatResponse.iconColor ?: "FFFFFF",
                        imageBase64 = getChatResponse.image,
                        removeBranding = getChatResponse.removeBranding?.toBoolean() ?: false,
                    ),
                )
            }
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
