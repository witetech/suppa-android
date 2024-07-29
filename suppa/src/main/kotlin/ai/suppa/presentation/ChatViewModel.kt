package ai.suppa.presentation

import ai.suppa.data.SuppaService
import ai.suppa.data.models.*
import ai.suppa.data.models.AddMessageRequest
import ai.suppa.domain.models.*
import ai.suppa.domain.models.Theme
import ai.suppa.presentation.core.StateDelegate
import ai.suppa.presentation.core.StateViewModel
import ai.suppa.presentation.utils.DateUtils
import androidx.lifecycle.ViewModel
import kotlinx.serialization.json.Json
import java.util.Date

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
                    config = mapChatResponseToConfig(getChatResponse, apiKey, chatId),
                    theme = mapChatResponseToTheme(getChatResponse),
                    messages = mapChatResponseToMessages(getChatResponse),
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            stateDelegate.updateState { State.Error("Something went wrong") }
        }
    }

    suspend fun reloadChat() {
        val currentState = stateDelegate.state.value as? State.Content ?: return
        try {
            val getChatResponse = suppaService.getChat(
                apiKey = currentState.config.apiKey,
                chatId = currentState.config.chatId,
            )

            stateDelegate.updateState {
                State.Content(
                    config = mapChatResponseToConfig(
                        getChatResponse,
                        currentState.config.apiKey,
                        currentState.config.chatId,
                    ),
                    theme = mapChatResponseToTheme(getChatResponse),
                    messages = mapChatResponseToMessages(getChatResponse),
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
            stateDelegate.updateState { State.Error("Something went wrong") }
        }
    }

    suspend fun addMessage(message: String) {
        val currentState = stateDelegate.state.value as? State.Content ?: return

        stateDelegate.updateState {
            currentState.copy(
                messages = currentState.messages + Message(
                    id = "0",
                    content = MessageContent(
                        content = message,
                        type = MessageContent.Type.USER_MESSAGE,
                        value = null,
                    ),
                    role = Message.Role.USER,
                    createdAt = Date(),
                ),
            )
        }

        val messageRequest = AddMessageRequest(
            content = message,
            type = "message",
        )
        try {
            suppaService.addMessage(
                apiKey = currentState.config.apiKey,
                chatId = currentState.config.chatId,
                addMessageRequest = messageRequest,
            )
            reloadChat()
        } catch (e: Exception) {
            e.printStackTrace()
            stateDelegate.updateState { State.Error("Something went wrong") }
        }
    }

    private fun mapChatResponseToConfig(
        chatResponse: GetChatResponse,
        apiKey: String,
        chatId: String,
    ): Config {
        return Config(
            chatId = chatId,
            name = chatResponse.name!!,
            description = null,
            apiKey = apiKey,
        )
    }

    private fun mapChatResponseToTheme(chatResponse: GetChatResponse): Theme {
        return Theme(
            colorHex = chatResponse.color ?: "000000",
            textColorHex = chatResponse.textColor ?: "FFFFFF",
            iconColorHex = chatResponse.iconColor ?: "FFFFFF",
            imageBase64 = chatResponse.image,
            removeBranding = chatResponse.removeBranding?.toBoolean() ?: false,
        )
    }

    private fun mapChatResponseToMessages(chatResponse: GetChatResponse): List<Message> {
        return chatResponse.messages?.mapNotNull { message ->
            message?.let {
                Message(
                    id = it.id!!,
                    content = mapAddMessageResponseOutputStringToMessageContent(it.content!!),
                    role = when (it.role) {
                        "user" -> Message.Role.USER
                        "assistant" -> Message.Role.ASSISTANT
                        else -> Message.Role.UNKNOWN
                    },
                    createdAt = DateUtils.parseDate(it.createdAt!!),
                )
            }
        } ?: arrayListOf()
    }

    private fun mapAddMessageResponseOutputToMessageContent(output: AddMessageResponse.Output): MessageContent {
        return MessageContent(
            content = output.content,
            type = when (output.type) {
                "message" -> MessageContent.Type.MESSAGE
                "input" -> MessageContent.Type.INPUT
                "message-user" -> MessageContent.Type.USER_MESSAGE
                else -> MessageContent.Type.UNKNOWN
            },
            value = output.value,
        )
    }

    private fun mapAddMessageResponseOutputStringToMessageContent(outputString: String): MessageContent {
        val output = Json.decodeFromString<AddMessageResponse.Output>(outputString)
        return mapAddMessageResponseOutputToMessageContent(output)
    }

    sealed interface State {
        data object Loading : State

        data class Error(val message: String) : State

        data class Content(
            val config: Config,
            val theme: Theme,
            val messages: List<Message>,
        ) : State
    }
}
