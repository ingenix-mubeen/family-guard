package com.ingenious.familyguard.util

import android.content.Context
import android.content.Intent
import android.os.Build
import com.ingenious.familyguard.security.MonitoringService

object ServiceUtils {
    fun startMonitoringService(context: Context) {
        val intent = Intent(context, MonitoringService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(intent)
        } else {
            context.startService(intent)
        }
    }

    fun stopMonitoringService(context: Context) {
        val intent = Intent(context, MonitoringService::class.java)
        context.stopService(intent)
    }
}
