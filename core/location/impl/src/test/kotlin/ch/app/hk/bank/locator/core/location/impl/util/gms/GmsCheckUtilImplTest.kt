package ch.app.hk.bank.locator.core.location.impl.util.gms

import android.content.Context
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import io.kotest.matchers.shouldBe
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Test

class GmsCheckUtilImplTest {
    private val context = mockk<Context>()
    private val googleApiAvailability = mockk<GoogleApiAvailability>()

    private val googleApiCheckUtil = GmsCheckUtilImpl(context, googleApiAvailability)

    @Test
    fun `isGoogleAvailable returns true when Google Play services are available`() {
        every { googleApiAvailability.isGooglePlayServicesAvailable(context) } returns ConnectionResult.SUCCESS

        val result = googleApiCheckUtil.isGmsAvailable()

        result shouldBe true
    }

    @Test
    fun `isGoogleAvailable returns false when Google Play services are not available`() {
        every { googleApiAvailability.isGooglePlayServicesAvailable(context) } returns ConnectionResult.SERVICE_MISSING

        val result = googleApiCheckUtil.isGmsAvailable()

        result shouldBe false
    }
}
