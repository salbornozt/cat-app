package com.satdev.thecatsapp.data.api

import com.satdev.thecatsapp.data.model.Breed
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers

interface ApiService {
    @Headers("API-KEY: x-api-key: bda53789-d59e-46cd-9bc4-2936630fde39")
    @GET("breeds")
    suspend fun getAllBreeds() : Response<List<Breed>>
}