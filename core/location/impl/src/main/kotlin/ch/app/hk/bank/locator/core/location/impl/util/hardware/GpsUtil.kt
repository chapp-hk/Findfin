package ch.app.hk.bank.locator.core.location.impl.util.hardware

interface GpsUtil {
    fun hasGpsSensor(): Boolean

    fun isGpsEnabled(): Boolean
}
