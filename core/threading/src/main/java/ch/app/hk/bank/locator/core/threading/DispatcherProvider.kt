package ch.app.hk.bank.locator.core.threading

import kotlinx.coroutines.Dispatchers

class DispatcherProvider {
    fun io() = Dispatchers.IO

    fun default() = Dispatchers.Default
}
