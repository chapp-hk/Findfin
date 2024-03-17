package ch.app.hk.bank.locator.core.design.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue

@Composable
fun <T> ScreenStateView(
    state: State<ScreenState<T>>,
    empty: @Composable () -> Unit = {},
    error: @Composable (data: T) -> Unit = {},
    loading: @Composable () -> Unit = {},
    success: @Composable (T) -> Unit,
) {
    val stateValue by state
    when (val value = stateValue) {
        ScreenState.Empty -> empty()
        is ScreenState.Error -> error(value.data)
        ScreenState.Loading -> loading()
        is ScreenState.Success -> success(value.data)
    }
}

@Stable
sealed interface ScreenState<out T> {
    data object Empty : ScreenState<Nothing>

    data object Loading : ScreenState<Nothing>

    data class Error<T>(val data: T) : ScreenState<T>

    data class Success<T>(val data: T) : ScreenState<T>
}
