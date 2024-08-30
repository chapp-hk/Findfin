package ch.app.hk.bank.locator.feature.bank.data.remote.model

data class LocatorApiError(
    val errorCode: String,
    val errorMessage: String,
) : Throwable()
