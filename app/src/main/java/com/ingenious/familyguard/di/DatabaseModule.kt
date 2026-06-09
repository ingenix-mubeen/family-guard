package com.ingenious.familyguard.di

import android.content.Context
import androidx.room.Room
import com.ingenious.familyguard.data.local.WhitelistDao
import com.ingenious.familyguard.data.local.WhitelistDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideWhitelistDatabase(@ApplicationContext context: Context): WhitelistDatabase {
        return Room.databaseBuilder(
            context,
            WhitelistDatabase::class.java,
            "whitelist_db"
        ).fallbackToDestructiveMigration().build()
    }

    @Provides
    @Singleton
    fun provideWhitelistDao(database: WhitelistDatabase): WhitelistDao {
        return database.whitelistDao()
    }
}
