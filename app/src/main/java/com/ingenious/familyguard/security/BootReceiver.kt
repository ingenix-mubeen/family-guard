package com.ingenious.familyguard.security

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.ingenious.familyguard.domain.repository.SettingsRepository
import com.ingenious.familyguard.util.ServiceUtils
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var settingsRepository: SettingsRepository

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == Intent.ACTION_BOOT_COMPLETED) {
            CoroutineScope(Dispatchers.IO).launch {
                val isEnabled = settingsRepository.isProtectionEnabled().firstOrNull() ?: false
                if (isEnabled) {
                    ServiceUtils.startMonitoringService(context)
                }
            }
        }
    }
}
