package com.glacierpower.pexelsapp.di

import com.glacierpower.pexelsapp.domain.pexels.PexelsInteractor
import com.glacierpower.pexelsapp.domain.pexels.PexelsRepository
import com.glacierpower.pexelsapp.domain.setting.SettingInteractor
import com.glacierpower.pexelsapp.domain.setting.SettingRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun providePexelsInteractor(
        pixelsRepository: PexelsRepository
    ): PexelsInteractor {
        return PexelsInteractor(pixelsRepository)
    }

    @Provides
    fun provideSettingInteractor(
        settingRepository: SettingRepository
    ): SettingInteractor {
        return SettingInteractor(settingRepository)
    }
}