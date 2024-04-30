package ch.app.hk.bank.locator.app.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.compose.rememberNavController
import ch.app.hk.bank.locator.app.navigation.AppNavHost
import ch.app.hk.bank.locator.core.design.ui.AppContent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        setContent {
            AppContent {
                val navController = rememberNavController()
                navController.setLifecycleOwner(this)
                navController.setOnBackPressedDispatcher(onBackPressedDispatcher)
                navController.enableOnBackPressed(enabled = true)

                AppNavHost(navController = navController)
            }
        }
    }
}
