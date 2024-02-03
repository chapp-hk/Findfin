package ch.app.hk.bank.locator.core.design.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

sealed interface ScreenState<out T> {
    data object Empty : ScreenState<Nothing>
    data object Loading : ScreenState<Nothing>
    data class Error<T>(val cause: Throwable, val data: T) : ScreenState<T>
    data class Success<T>(val data: T) : ScreenState<T>
}

@Composable
fun <T> ScreenStateView(
    modifier: Modifier = Modifier,
    state: ScreenState<T>,
    empty: @Composable () -> Unit = {},
    error: @Composable (Throwable, data: T) -> Unit = { _, _ -> },
    loading: @Composable () -> Unit = {},
    success: @Composable (T) -> Unit,
) {
    when (state) {
        ScreenState.Empty -> empty()
        is ScreenState.Error -> error(state.cause, state.data)
        ScreenState.Loading -> loading()
        is ScreenState.Success -> success(state.data)
    }
}
