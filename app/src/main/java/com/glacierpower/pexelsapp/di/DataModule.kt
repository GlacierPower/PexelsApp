package com.glacierpower.pexelsapp.di

import android.content.Context
import com.glacierpower.pexelsapp.data.repositoryImpl.PexelsRepositoryImpl
import com.glacierpower.pexelsapp.data.service.PexelsApiService
import com.glacierpower.pexelsapp.domain.PexelsRepository
import com.glacierpower.pexelsapp.utils.Constants.API_KEY
import com.glacierpower.pexelsapp.utils.Constants.BASE_URL
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindPexelsRepository(
        pexelsRepositoryImpl: PexelsRepositoryImpl
    ): PexelsRepository

    companion object {

        private const val HEADER_AUTHORIZATION = "Authorization:"


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
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        @Provides
        fun provideOkhttpClient(
            httpLoggingInterceptor: HttpLoggingInterceptor,
            context: Context
        ): OkHttpClient {
            val httpCacheDirectory = File(context.cacheDir, "http-cache")
            val cacheSize: Long = 10 * 1024 * 1024
            val cache = Cache(httpCacheDirectory, cacheSize)

            return OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor { chain ->
                    val originalRequest = chain.request()

                    val newRequestBuilder = originalRequest.newBuilder()
                        .header(HEADER_AUTHORIZATION, API_KEY)

                    val newRequest = newRequestBuilder.build()
                    chain.proceed(newRequest)
                }
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .cache(cache)
                .build()
        }


        @Provides
        fun provideLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }
}