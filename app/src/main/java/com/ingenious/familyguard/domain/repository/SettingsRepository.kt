package com.ingenious.familyguard.domain.repository

import kotlinx.coroutines.flow.Flow

interface SettingsRepository {
    fun getPin(): Flow<String?>
    suspend fun setPin(pin: String)
    suspend fun verifyPin(pin: String): Boolean
    fun isProtectionEnabled(): Flow<Boolean>
    suspend fun setProtectionEnabled(enabled: Boolean)
}
