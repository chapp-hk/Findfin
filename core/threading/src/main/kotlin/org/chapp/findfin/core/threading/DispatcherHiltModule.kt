package org.chapp.findfin.core.threading

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DispatcherHiltModule {
    @Provides
    @Singleton
    @DispatcherIo
    fun provideIoDispatcher() = Dispatchers.IO

    @Provides
    @Singleton
    @DispatcherDefault
    fun provideDefaultDispatcher() = Dispatchers.Default
}
