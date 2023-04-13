package com.satdev.thecatsapp.data.repository.dataSource

import com.satdev.thecatsapp.data.model.Breed
import retrofit2.Response

interface BreedRemoteDataSource {

    suspend fun getBreed(): Response<List<Breed>>

}