package ch.app.hk.bank.locator.core.logging.startup

import android.content.Context
import androidx.startup.Initializer
import co.touchlab.kermit.DefaultFormatter
import co.touchlab.kermit.Logger
import co.touchlab.kermit.platformLogWriter

class AppLoggerInitializer : Initializer<Logger> {
    override fun create(context: Context): Logger {
        if (BuildConfig.DEBUG) {
            Logger.setLogWriters(platformLogWriter(DefaultFormatter))
        } else {
            Logger.setLogWriters()
        }

        return Logger
    }

    override fun dependencies(): List<Class<out Initializer<*>>> {
        return listOf()
    }
}
