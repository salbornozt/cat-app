package com.satdev.thecatsapp.data.api

import com.satdev.thecatsapp.data.model.Breed
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("breeds")
    suspend fun getAllBreeds() : Response<List<Breed>>
}