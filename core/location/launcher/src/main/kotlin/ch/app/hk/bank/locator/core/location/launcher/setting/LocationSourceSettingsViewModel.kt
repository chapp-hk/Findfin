package ch.app.hk.bank.locator.core.location.launcher.setting

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationSourceSettingsViewModel @Inject constructor(
    val locationSourceSettingsResultContract: LocationSourceSettingsResultContract,
) : ViewModel()
