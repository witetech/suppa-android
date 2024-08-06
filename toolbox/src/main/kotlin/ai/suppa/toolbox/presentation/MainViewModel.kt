package ai.suppa.toolbox.presentation

import ai.suppa.toolbox.data.SuppaService
import ai.suppa.toolbox.data.models.GetChatbotsResponse
import ai.suppa.toolbox.domain.models.Chatbot
import ai.suppa.toolbox.presentation.core.StateDelegate
import ai.suppa.toolbox.presentation.core.StateViewModel
import androidx.lifecycle.ViewModel

class MainViewModel(
    private val suppaService: SuppaService = SuppaService(),
    private val stateDelegate: StateDelegate<State> = StateDelegate(),
) : ViewModel(), StateViewModel<MainViewModel.State> by stateDelegate {
    init {
        stateDelegate.setDefaultState(State.Loading)
    }

    suspend fun loadChatbots() {
        stateDelegate.setDefaultState(State.Loading)
        try {
            val getChatbotsResponse = suppaService.getChatbots()
            val chatbots = mapChatbotsResponseToChatbots(getChatbotsResponse)
            stateDelegate.updateState { State.Content(chatbots) }
        } catch (e: Exception) {
            e.printStackTrace()
            stateDelegate.updateState { State.Error("Something went wrong") }
        }
    }

    private fun mapChatbotsResponseToChatbots(getChatbotsResponse: GetChatbotsResponse): List<Chatbot> {
        return getChatbotsResponse.chatbots?.filterNotNull()
            ?.filter { it.id != null && it.name != null }?.map {
                Chatbot(
                    id = it.id!!,
                    name = it.name!!,
                    companyName = it.companyName,
                    companyDescription = it.companyDescription,
                )
            } ?: arrayListOf()
    }

    sealed interface State {
        data object Loading : State

        data class Error(val message: String) : State

        data class Content(val chatbots: List<Chatbot>) : State
    }
}
