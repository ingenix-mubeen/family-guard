package com.ingenious.familyguard.data.repository

import com.ingenious.familyguard.data.datastore.SettingsDataStore
import com.ingenious.familyguard.domain.repository.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SettingsRepositoryImpl @Inject constructor(
    private val settingsDataStore: SettingsDataStore
) : SettingsRepository {
    override fun getPin(): Flow<String?> = settingsDataStore.pinFlow

    override suspend fun setPin(pin: String) {
        settingsDataStore.setPin(pin)
    }

    override suspend fun verifyPin(pin: String): Boolean {
        val currentPin = settingsDataStore.pinFlow.firstOrNull()
        return currentPin != null && currentPin == pin
    }

    override fun isProtectionEnabled(): Flow<Boolean> = settingsDataStore.protectionEnabledFlow

    override suspend fun setProtectionEnabled(enabled: Boolean) {
        settingsDataStore.setProtectionEnabled(enabled)
    }
}
