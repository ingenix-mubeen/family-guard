package com.ingenious.familyguard.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AllowedAppEntity::class], version = 1, exportSchema = false)
abstract class WhitelistDatabase : RoomDatabase() {
    abstract fun whitelistDao(): WhitelistDao
}
