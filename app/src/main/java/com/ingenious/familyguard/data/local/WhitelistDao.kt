package com.ingenious.familyguard.data.local

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WhitelistDao {
    @Query("SELECT * FROM allowed_apps ORDER BY appName ASC")
    fun getAllAllowedApps(): Flow<List<AllowedAppEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAllowedApp(app: AllowedAppEntity)

    @Query("DELETE FROM allowed_apps WHERE packageName = :packageName")
    suspend fun removeAllowedApp(packageName: String)

    @Query("SELECT EXISTS(SELECT 1 FROM allowed_apps WHERE packageName = :packageName)")
    suspend fun isAppAllowed(packageName: String): Boolean
}
