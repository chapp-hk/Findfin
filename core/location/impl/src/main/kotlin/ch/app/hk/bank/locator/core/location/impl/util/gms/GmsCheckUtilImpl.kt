package ch.app.hk.bank.locator.core.location.impl.util.gms

import android.content.Context
import ch.app.framework.hiltext.annotation.HiltExtBindModule
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltExtBindModule
class GmsCheckUtilImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val googleApiAvailability: GoogleApiAvailability,
) : GmsCheckUtil {
    override fun isGmsAvailable(): Boolean {
        return googleApiAvailability.isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS
    }
}
