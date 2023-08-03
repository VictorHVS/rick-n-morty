package com.victorhvs.rnm.data.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.victorhvs.rnm.core.DispatcherProvider
import com.victorhvs.rnm.core.DispatcherProviderImpl
import com.victorhvs.rnm.data.datasources.remote.RNMService
import com.victorhvs.rnm.data.repositories.CharacterRepository
import com.victorhvs.rnm.data.repositories.CharacterRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalSerializationApi
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideDisparcherProvider(): DispatcherProvider = DispatcherProviderImpl()

    @Provides
    @Singleton
    fun provideHttpLogging(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(RNMService.TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .connectTimeout(RNMService.TIMEOUT_IN_SECONDS, TimeUnit.SECONDS)
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofitInstance(okHttpClient: OkHttpClient): Retrofit {
        val contentType = "application/json".toMediaType()
        val jsonConverter = Json {
            ignoreUnknownKeys = true
        }.asConverterFactory(contentType = contentType)

        return Retrofit.Builder()
            .baseUrl(RNMService.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(jsonConverter)
            .build()
    }

    @Provides
    @Singleton
    fun provideRNMService(retrofit: Retrofit): RNMService = retrofit.create(RNMService::class.java)

    @Provides
    @Singleton
    fun provideCharacterRepository(
        rnmService: RNMService,
        dispacher: DispatcherProvider
    ): CharacterRepository = CharacterRepositoryImpl(
        dispatcher = dispacher,
        rnmService = rnmService,
    )
}
