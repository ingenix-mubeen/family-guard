package com.ingenious.familyguard.security

import android.accessibilityservice.AccessibilityService
import android.content.Intent
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import com.ingenious.familyguard.domain.repository.SettingsRepository
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class InstallationAccessibilityService : AccessibilityService() {

    @Inject
    lateinit var settingsRepository: SettingsRepository

    private val serviceJob = SupervisorJob()
    private val serviceScope = CoroutineScope(Dispatchers.Main + serviceJob)

    private val INSTALLER_PACKAGES = setOf(
        "com.google.android.packageinstaller",
        "com.android.packageinstaller",
        "com.samsung.android.packageinstaller",
        "com.miui.packageinstaller"
    )
    private val PLAY_STORE_PACKAGE = "com.android.vending"

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        if (event == null) return

        val packageName = event.packageName?.toString() ?: return
        
        serviceScope.launch {
            val isEnabled = settingsRepository.isProtectionEnabled().firstOrNull() ?: false
            if (!isEnabled) return@launch

            if (INSTALLER_PACKAGES.contains(packageName) || packageName == PLAY_STORE_PACKAGE) {
                if (event.eventType == AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED) {
                    Log.d("FamilyGuard", "Blocking access to $packageName")
                    // Perform back action to close the installer/Play Store
                    performGlobalAction(GLOBAL_ACTION_BACK)
                    
                    // Optionally, redirect to the app's own PIN screen
                    // startPinVerificationActivity()
                }
            }
        }
    }

    override fun onInterrupt() {
        Log.d("FamilyGuard", "Accessibility Service Interrupted")
    }

    override fun onDestroy() {
        super.onDestroy()
        serviceJob.cancel()
    }
}
