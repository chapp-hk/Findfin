package ch.app.hk.bank.locator.feature.auth.data.repo.auth.repository

import ch.app.framework.hiltext.annotation.HiltExtBindModule
import ch.app.hk.bank.locator.core.preferences.api.AppPreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltExtBindModule
class AuthRepositoryImpl
    @Inject
    constructor(
        private val appPreferencesRepository: AppPreferencesRepository,
    ) : AuthRepository {
        private val keyIsAuthInitialized = "pref_key_is_auth_initialized"

        override fun isAuthInitialized(): Flow<Boolean> {
            return appPreferencesRepository.getBoolean(keyIsAuthInitialized)
        }

        override suspend fun setAuthInitialized() {
            appPreferencesRepository.setBoolean(keyIsAuthInitialized, true)
        }
    }
