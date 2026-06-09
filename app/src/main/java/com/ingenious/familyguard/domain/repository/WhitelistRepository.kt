package com.ingenious.familyguard.domain.repository

import com.ingenious.familyguard.domain.model.AllowedApp
import kotlinx.coroutines.flow.Flow

interface WhitelistRepository {
    fun getAllowedApps(): Flow<List<AllowedApp>>
    suspend fun addAllowedApp(app: AllowedApp)
    suspend fun removeAllowedApp(packageName: String)
    suspend fun isAppAllowed(packageName: String): Boolean
}
