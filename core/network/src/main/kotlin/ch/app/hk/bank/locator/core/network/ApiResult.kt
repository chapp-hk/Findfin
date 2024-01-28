package ch.app.hk.bank.locator.core.network

sealed interface ApiResult<out T> {
    data class Success<T>(val data: T) : ApiResult<T>

    data class Error(val cause: Throwable) : ApiResult<Nothing>
}
