package ch.app.hk.bank.locator.core.design.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

typealias ScreenStateFlow<T, E> = StateFlow<ScreenState<T, E>>

fun <T, E> mutableScreenStateFlowOf(initialValue: ScreenState<T, E>) = MutableStateFlow(initialValue)

@Composable
fun <T, E> ScreenStateView(
    state: State<ScreenState<T, E>>,
    empty: @Composable () -> Unit = {},
    error: @Composable (error: E) -> Unit = {},
    loading: @Composable () -> Unit = {},
    success: @Composable (T) -> Unit,
) {
    val stateValue by state
    when (val value = stateValue) {
        ScreenState.Empty -> empty()
        is ScreenState.Error -> error(value.error)
        ScreenState.Loading -> loading()
        is ScreenState.Success -> success(value.data)
    }
}

@Stable
sealed interface ScreenState<out T, out E> {
    data object Empty : ScreenState<Nothing, Nothing>

    data object Loading : ScreenState<Nothing, Nothing>

    data class Error<T, E>(val error: E) : ScreenState<T, E>

    data class Success<T, E>(val data: T) : ScreenState<T, E>
}
