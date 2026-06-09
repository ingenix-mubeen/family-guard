package com.ingenious.familyguard.domain.model

data class AllowedApp(
    val packageName: String,
    val appName: String,
    val addedAt: Long = System.currentTimeMillis()
)
