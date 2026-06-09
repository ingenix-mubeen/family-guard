package com.ingenious.familyguard.ui.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

sealed interface Route : NavKey {
    @Serializable
    data object Dashboard : Route
    
    @Serializable
    data object PinSetup : Route
    
    @Serializable
    data object PinEntry : Route
    
    @Serializable
    data object Whitelist : Route
    
    @Serializable
    data object Settings : Route
}
