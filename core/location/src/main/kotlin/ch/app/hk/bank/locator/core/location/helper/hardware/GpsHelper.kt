package ch.app.hk.bank.locator.core.location.helper.hardware

interface GpsHelper {
    fun hasGpsSensor(): Boolean

    fun isGpsEnabled(): Boolean
}
