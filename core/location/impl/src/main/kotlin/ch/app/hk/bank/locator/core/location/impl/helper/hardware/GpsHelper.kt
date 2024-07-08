package ch.app.hk.bank.locator.core.location.impl.helper.hardware

interface GpsHelper {
    fun hasGpsSensor(): Boolean

    fun isGpsEnabled(): Boolean
}
