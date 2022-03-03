package com.example.moviesappdemo.datasource.data.di

import com.example.moviesappdemo.datasource.data.MovieRemoteDataSource
import com.example.moviesappdemo.datasource.data.MovieRemoteDataSourceImpl
import com.example.moviesappdemo.datasource.network.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideMovieRemoteDataSource(apiService: ApiService):
            MovieRemoteDataSource = MovieRemoteDataSourceImpl(apiService)

/*    @Provides
    @Singleton
    fun provideDeviceUseCase(deviceRepository: DeviceRepository):
            DeviceUseCase = DeviceUseCaseImpl(deviceRepository = deviceRepository)*/
}