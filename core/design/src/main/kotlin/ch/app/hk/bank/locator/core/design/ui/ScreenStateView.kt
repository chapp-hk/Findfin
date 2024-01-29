package ch.app.hk.bank.locator.core.design.ui

import androidx.compose.runtime.Composable

sealed interface ScreenState<out T> {
    data object Empty : ScreenState<Nothing>
    data object Loading : ScreenState<Nothing>
    data class Error(val cause: Throwable) : ScreenState<Nothing>
    data class Success<T>(val data: T) : ScreenState<T>
}

@Composable
fun <T> ScreenStateComponent(
    state: ScreenState<T>,
    empty: @Composable () -> Unit = {},
    error: @Composable (Throwable) -> Unit = {},
    loading: @Composable () -> Unit = {},
    success: @Composable (T) -> Unit,
) {
    when (state) {
        ScreenState.Empty -> empty()
        is ScreenState.Error -> error(state.cause)
        ScreenState.Loading -> loading()
        is ScreenState.Success -> success(state.data)
    }
}
