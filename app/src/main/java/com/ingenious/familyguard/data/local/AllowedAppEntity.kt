package com.ingenious.familyguard.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.ingenious.familyguard.domain.model.AllowedApp

@Entity(tableName = "allowed_apps")
data class AllowedAppEntity(
    @PrimaryKey val packageName: String,
    val appName: String,
    val addedAt: Long
)

fun AllowedAppEntity.toDomain() = AllowedApp(
    packageName = packageName,
    appName = appName,
    addedAt = addedAt
)

fun AllowedApp.toEntity() = AllowedAppEntity(
    packageName = packageName,
    appName = appName,
    addedAt = addedAt
)
