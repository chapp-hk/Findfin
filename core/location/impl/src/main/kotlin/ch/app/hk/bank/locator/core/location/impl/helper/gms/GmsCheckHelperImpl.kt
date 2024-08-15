package ch.app.hk.bank.locator.core.location.impl.helper.gms

import android.content.Context
import ch.app.library.hiltwrap.annotation.HiltWrapBindModule
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltWrapBindModule
internal class GmsCheckHelperImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val googleApiAvailability: GoogleApiAvailability,
) : GmsCheckHelper {
    override fun isGmsAvailable(): Boolean {
        return googleApiAvailability.isGooglePlayServicesAvailable(context) == ConnectionResult.SUCCESS
    }
}
