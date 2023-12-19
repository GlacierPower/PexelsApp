package com.glacierpower.pexelsapp.di

import com.glacierpower.pexelsapp.domain.PexelsInteractor
import com.glacierpower.pexelsapp.domain.PexelsRepository
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
}