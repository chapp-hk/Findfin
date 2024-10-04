package org.chapp.findfin.feature.auth.navigation.viewmodel

internal sealed interface AuthNavState {
    data object Loading : AuthNavState

    data object IsInitialized : AuthNavState

    data object NotInitialized : AuthNavState
}
