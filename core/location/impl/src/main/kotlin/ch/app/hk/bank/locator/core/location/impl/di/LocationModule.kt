package ch.app.hk.bank.locator.core.location.impl.di

import android.content.Context
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class LocationModule {
    @Provides
    fun provideFusedLocationProviderClient(
        @ApplicationContext context: Context,
    ) = LocationServices.getFusedLocationProviderClient(context)

    @Provides
    fun providesGoogleApiAvailability() = GoogleApiAvailability.getInstance()
}
