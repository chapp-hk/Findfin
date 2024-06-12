package ch.app.hk.bank.locator.core.location.impl.helper.hardware

internal interface GpsHelper {
    fun hasGpsSensor(): Boolean

    fun isGpsEnabled(): Boolean
}
