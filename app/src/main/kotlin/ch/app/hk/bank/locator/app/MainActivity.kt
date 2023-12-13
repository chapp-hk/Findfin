package ch.app.hk.bank.locator.app

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import ch.app.hk.bank.locator.core.design.theme.AppTheme
import ch.app.hk.bank.locator.feature.locator.data.remote.datasource.LocatorRemoteDataSource
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    @Inject
    lateinit var LocatorRemoteDataSource: LocatorRemoteDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            AppTheme {

            }
        }
    }
}
