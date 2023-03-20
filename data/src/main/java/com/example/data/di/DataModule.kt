package com.example.data.di

import android.content.Context
import androidx.room.Room
import com.example.data.BuildConfig
import com.example.data.local.dbManager.AppDatabase
import com.example.data.remote.services.ApiServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.android.*
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.*
import kotlinx.serialization.json.Json

import io.ktor.client.features.logging.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DataModule {


    @Singleton
    @Provides
    fun provideBlogDb(@ApplicationContext context: Context): AppDatabase {
        return Room
            .databaseBuilder(
                context,
                AppDatabase::class.java,
                AppDatabase.DATABASE_NAME
            )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }


    @Provides
    @Singleton
    fun provideWeatherDao(appDatabase: AppDatabase) = appDatabase.weatherDao()


    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Singleton
    @Provides
    fun providesOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient
            .Builder()
            .connectTimeout(1, TimeUnit.MINUTES)
            .writeTimeout(1, TimeUnit.MINUTES)
            .readTimeout(1, TimeUnit.MINUTES)
            .addInterceptor(httpLoggingInterceptor)
            .build()

    @Singleton
    @Provides
    fun provideListRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BuildConfig.baseUrl)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiServices =
        retrofit.create(ApiServices::class.java)

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient = HttpClient(Android) {
        install(Logging) {
            level = LogLevel.ALL
        }
        install(JsonFeature) {
            serializer = KotlinxSerializer(Json {
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }


}