package ch.app.hk.bank.locator.app.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import ch.app.hk.bank.locator.app.navigation.AppNavHost
import ch.app.hk.bank.locator.app.viewmodel.MainViewModel
import ch.app.hk.bank.locator.core.design.ui.AppContent
import ch.app.hk.bank.locator.feature.setting.data.repo.preferece.model.Theme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        collectThemeFlow()

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

    private fun collectThemeFlow() {
        mainViewModel
            .themeFlow
            .onEach { theme ->
                val mode =
                    when (theme) {
                        Theme.LIGHT -> AppCompatDelegate.MODE_NIGHT_NO
                        Theme.DARK -> AppCompatDelegate.MODE_NIGHT_YES
                        Theme.SYSTEM -> AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
                        null -> null
                    }

                if (mode != null) {
                    AppCompatDelegate.setDefaultNightMode(mode)
                }
            }
            .launchIn(lifecycleScope)
    }
}
