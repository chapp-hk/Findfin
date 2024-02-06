package ch.app.hk.bank.locator.core.location

import android.app.Service
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.os.IBinder

class LocationService : Service(), LocationListener {
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onLocationChanged(location: Location) {
    }
}
