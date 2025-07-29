package org.chapp.findfin.feature.auth.data.remote.firebase.di

import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class AuthHiltModule {
    @Provides
    @Singleton
    fun providesFirebaseAuth() = Firebase.auth
}
