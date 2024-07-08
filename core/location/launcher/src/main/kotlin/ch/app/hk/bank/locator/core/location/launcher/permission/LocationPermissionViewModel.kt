package ch.app.hk.bank.locator.core.location.launcher.permission

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationPermissionViewModel @Inject constructor(
    val locationPermissionResultContract: LocationPermissionResultContract,
) : ViewModel()
