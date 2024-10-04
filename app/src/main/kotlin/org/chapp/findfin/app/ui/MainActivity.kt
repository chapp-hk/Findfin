package org.chapp.findfin.app.ui

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.lifecycleScope
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.chapp.findfin.app.navigation.AppNavHost
import org.chapp.findfin.app.viewmodel.MainViewModel
import org.chapp.findfin.core.design.ui.AppContent

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
            .themeModeFlow
            .onEach { it?.let(AppCompatDelegate::setDefaultNightMode) }
            .launchIn(lifecycleScope)
    }
}
