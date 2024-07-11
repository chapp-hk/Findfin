package ch.app.hk.bank.locator.core.location.impl.di

import ch.app.hk.bank.locator.core.location.impl.helper.hardware.GpsHelper
import ch.app.hk.bank.locator.core.location.impl.helper.permission.PermissionHelper
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface LocationHelperEntryPoint {
    fun getPermissionHelper(): PermissionHelper

    fun getGpsHelper(): GpsHelper
}
