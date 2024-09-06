package ch.app.hk.bank.locator.core.location.di

import ch.app.hk.bank.locator.core.location.helper.hardware.GpsHelper
import ch.app.hk.bank.locator.core.location.helper.permission.PermissionHelper
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface LocationHelperEntryPoint {
    fun getPermissionHelper(): PermissionHelper

    fun getGpsHelper(): GpsHelper
}
