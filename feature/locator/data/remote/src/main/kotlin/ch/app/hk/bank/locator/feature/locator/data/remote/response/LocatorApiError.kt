package ch.app.hk.bank.locator.feature.locator.data.remote.response

data class LocatorApiError(
    val errorCode: String,
    val errorMessage: String,
) : Throwable()
