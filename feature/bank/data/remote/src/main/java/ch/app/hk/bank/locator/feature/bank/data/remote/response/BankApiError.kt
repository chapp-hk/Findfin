package ch.app.hk.bank.locator.feature.bank.data.remote.response

data class BankApiError(
    val errorCode: String,
    val errorMessage: String,
) : Throwable()
