package com.satdev.thecatsapp.data.repository.dataSourceImpl

import com.satdev.thecatsapp.data.api.ApiService
import com.satdev.thecatsapp.data.model.Breed
import com.satdev.thecatsapp.data.repository.dataSource.BreedRemoteDataSource
import retrofit2.Response
import javax.inject.Inject

class BreedRemoteDataSourceImpl @Inject constructor(private val apiService: ApiService) : BreedRemoteDataSource {
    override suspend fun getBreed(): Response<List<Breed>> {
        return apiService.getAllBreeds()
    }
}