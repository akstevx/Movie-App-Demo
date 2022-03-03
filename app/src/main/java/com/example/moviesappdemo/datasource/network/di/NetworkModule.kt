package com.example.moviesappdemo.datasource.network.di

//import androidx.viewbinding.BuildConfig
import com.example.moviesappdemo.BuildConfig
import com.example.moviesappdemo.datasource.network.api.ApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val MOVIES_BASE_URL = "https://api.themoviedb.org/"

    @Provides
    @Singleton
    fun provideNetworkURL(): String = MOVIES_BASE_URL

    @Provides
    @Singleton
    fun provideHttpLogger(): HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level =
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
    }

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory.create(gson)

    private fun okHttpClient(logger: HttpLoggingInterceptor) = OkHttpClient.Builder()
        .addInterceptor((logger))
        .readTimeout(3, TimeUnit.MINUTES)
        .connectTimeout(3, TimeUnit.MINUTES)
        .writeTimeout(3, TimeUnit.MINUTES)
        .build()


    @Provides
    @Singleton
    fun provideRetrofit(
        logger: HttpLoggingInterceptor,
        converterFactory: GsonConverterFactory
    ): Retrofit =
        Retrofit.Builder().baseUrl(provideNetworkURL()).client(okHttpClient(logger))
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(converterFactory)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .build()

    @Provides
    @Singleton
    fun provideAPIService(retrofit: Retrofit): ApiService =
        retrofit.create(ApiService::class.java)

}