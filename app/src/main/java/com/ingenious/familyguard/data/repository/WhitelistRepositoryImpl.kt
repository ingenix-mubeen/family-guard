package com.ingenious.familyguard.data.repository

import com.ingenious.familyguard.data.local.WhitelistDao
import com.ingenious.familyguard.data.local.toDomain
import com.ingenious.familyguard.data.local.toEntity
import com.ingenious.familyguard.domain.model.AllowedApp
import com.ingenious.familyguard.domain.repository.WhitelistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WhitelistRepositoryImpl @Inject constructor(
    private val whitelistDao: WhitelistDao
) : WhitelistRepository {
    override fun getAllowedApps(): Flow<List<AllowedApp>> {
        return whitelistDao.getAllAllowedApps().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun addAllowedApp(app: AllowedApp) {
        whitelistDao.addAllowedApp(app.toEntity())
    }

    override suspend fun removeAllowedApp(packageName: String) {
        whitelistDao.removeAllowedApp(packageName)
    }

    override suspend fun isAppAllowed(packageName: String): Boolean {
        return whitelistDao.isAppAllowed(packageName)
    }
}
