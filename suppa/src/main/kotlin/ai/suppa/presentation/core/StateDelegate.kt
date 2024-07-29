package ai.suppa.presentation.core

import kotlinx.coroutines.flow.*

internal interface StateViewModel<State> {
    val state: StateFlow<State>
}

internal class StateDelegate<State> constructor() : StateViewModel<State> {
    private lateinit var _state: MutableStateFlow<State>
    override val state: StateFlow<State>
        get() {
            return _state.asStateFlow()
        }

    fun setDefaultState(state: State) {
        _state = MutableStateFlow(state)
    }

    fun updateState(block: (State) -> State) {
        _state.update {
            block(it)
        }
    }

    inline fun <reified SubState : State> onState(block: (SubState) -> Unit) {
        val currentState = state.value
        if (currentState is SubState) {
            block(currentState)
        }
    }
}
