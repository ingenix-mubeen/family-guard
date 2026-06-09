package com.ingenious.familyguard.di

import com.ingenious.familyguard.data.repository.SettingsRepositoryImpl
import com.ingenious.familyguard.data.repository.WhitelistRepositoryImpl
import com.ingenious.familyguard.domain.repository.SettingsRepository
import com.ingenious.familyguard.domain.repository.WhitelistRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWhitelistRepository(
        whitelistRepositoryImpl: WhitelistRepositoryImpl
    ): WhitelistRepository

    @Binds
    @Singleton
    abstract fun bindSettingsRepository(
        settingsRepositoryImpl: SettingsRepositoryImpl
    ): SettingsRepository
}
