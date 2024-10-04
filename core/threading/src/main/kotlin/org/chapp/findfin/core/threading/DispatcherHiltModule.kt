package org.chapp.findfin.core.threading

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
internal class DispatcherHiltModule {
    @Provides
    @DispatcherIo
    fun provideIoDispatcher() = Dispatchers.IO

    @Provides
    @DispatcherDefault
    fun provideDefaultDispatcher() = Dispatchers.Default
}
