package ch.app.hk.bank.locator.feature.auth.data.repo.auth.repository

import ch.app.hk.bank.locator.core.preferences.api.AppPreferencesRepository
import ch.app.library.hiltwrap.annotation.HiltWrapBindModule
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@HiltWrapBindModule
class AuthRepositoryImpl @Inject constructor(
    private val appPreferencesRepository: AppPreferencesRepository,
) : AuthRepository {
    private val keyIsAuthInitialized = "pref_key_is_auth_initialized"

    override suspend fun isAuthInitialized(): Boolean {
        return appPreferencesRepository.getBoolean(keyIsAuthInitialized).first()
    }

    override suspend fun setAuthInitialized() {
        appPreferencesRepository.setBoolean(keyIsAuthInitialized, true)
    }
}
