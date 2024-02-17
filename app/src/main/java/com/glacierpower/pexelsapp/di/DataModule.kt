package com.glacierpower.pexelsapp.di

import com.glacierpower.pexelsapp.data.repositoryImpl.PexelsRepositoryImpl
import com.glacierpower.pexelsapp.data.repositoryImpl.SettingRepositoryImpl
import com.glacierpower.pexelsapp.data.service.GsonDeserializador
import com.glacierpower.pexelsapp.data.service.PexelsApiService
import com.glacierpower.pexelsapp.data.service.response.Photo
import com.glacierpower.pexelsapp.domain.pexels.PexelsRepository
import com.glacierpower.pexelsapp.domain.setting.SettingRepository
import com.glacierpower.pexelsapp.utils.Constants.BASE_URL
import com.google.gson.GsonBuilder
import com.google.gson.reflect.TypeToken
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindSettingRepository(
        settingRepositoryImpl: SettingRepositoryImpl
    ): SettingRepository

    @Binds
    abstract fun bindPexelsRepository(
        pexelsRepositoryImpl: PexelsRepositoryImpl
    ): PexelsRepository

    companion object {


        @Provides
        fun providePexelsApiService(retrofit: Retrofit): PexelsApiService {
            return retrofit.create(PexelsApiService::class.java)
        }

        @Provides
        fun provideBaseUrl(): String {
            return BASE_URL
        }

        @Provides
        fun provideRetrofit(
            baseUrl: String,
            client: OkHttpClient
        ): Retrofit {
            return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().registerTypeAdapter(
                            TypeToken.getParameterized(List::class.java, Photo::class.java).type,
                            GsonDeserializador()
                        ).create()
                    )
                )
                .client(client)
                .build()
        }

        @Provides
        fun provideOkHttpClientClient(interceptor: HttpLoggingInterceptor): OkHttpClient {
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .callTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
            return okHttpClient.build()
        }


        @Provides
        fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    }
}