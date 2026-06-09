package com.ingenious.familyguard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import com.ingenious.familyguard.domain.repository.SettingsRepository
import com.ingenious.familyguard.ui.navigation.Route
import com.ingenious.familyguard.ui.screens.dashboard.DashboardScreen
import com.ingenious.familyguard.ui.screens.dashboard.DashboardViewModel
import com.ingenious.familyguard.ui.screens.pin.PinScreen
import com.ingenious.familyguard.ui.screens.settings.SettingsScreen
import com.ingenious.familyguard.ui.screens.settings.SettingsViewModel
import com.ingenious.familyguard.ui.screens.whitelist.WhitelistScreen
import com.ingenious.familyguard.ui.screens.whitelist.WhitelistViewModel
import com.ingenious.familyguard.ui.theme.FamilyGuardTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var settingsRepository: SettingsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val hasPin by settingsRepository.getPin().collectAsState(initial = null)
            
            FamilyGuardTheme {
                if (hasPin == null) {
                    PinScreen(
                        isSetup = true,
                        onSuccess = { /* Handle PIN set */ },
                        viewModel = hiltViewModel()
                    )
                } else {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val backStack = rememberNavBackStack(Route.Dashboard as NavKey)
    
    val myEntryProvider = entryProvider<NavKey> {
        entry<Route.Dashboard> {
            DashboardScreen(
                onNavigateToWhitelist = { backStack.add(Route.Whitelist) },
                onNavigateToSettings = { backStack.add(Route.Settings) },
                viewModel = hiltViewModel<DashboardViewModel>()
            )
        }
        entry<Route.Whitelist> {
            WhitelistScreen(
                onBack = { backStack.removeLastOrNull() },
                viewModel = hiltViewModel<WhitelistViewModel>()
            )
        }
        entry<Route.Settings> {
            SettingsScreen(
                onBack = { backStack.removeLastOrNull() },
                viewModel = hiltViewModel<SettingsViewModel>()
            )
        }
    }

    NavDisplay(
        backStack = backStack,
        entryProvider = myEntryProvider,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberViewModelStoreNavEntryDecorator()
        )
    )
}
