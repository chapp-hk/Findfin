package org.chapp.findfin.core.threading

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DispatcherIo

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DispatcherDefault
