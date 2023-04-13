package com.satdev.thecatsapp.di

import com.satdev.thecatsapp.data.api.ApiService
import com.satdev.thecatsapp.data.repository.BreedRepositoryImpl
import com.satdev.thecatsapp.data.repository.dataSource.BreedRemoteDataSource
import com.satdev.thecatsapp.data.repository.dataSourceImpl.BreedRemoteDataSourceImpl
import com.satdev.thecatsapp.domain.repository.BreedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun providesRetrofit():Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.thecatapi.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun providesApiService(retrofit: Retrofit):ApiService {
        return retrofit.create(ApiService::class.java)
    }

    @Singleton
    @Provides
    fun providesBreedRepository(
        breedRemoteDataSource: BreedRemoteDataSource
    ): BreedRepository {
        return BreedRepositoryImpl(
            breedRemoteDataSource
        )
    }

    @Singleton
    @Provides
    fun providesBreedRemoteDataSource(apiService: ApiService):BreedRemoteDataSource{
        return BreedRemoteDataSourceImpl(apiService)
    }
}