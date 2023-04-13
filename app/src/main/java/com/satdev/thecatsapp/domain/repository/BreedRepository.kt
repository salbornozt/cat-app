package com.satdev.thecatsapp.domain.repository

import com.satdev.thecatsapp.data.model.Breed
import com.satdev.thecatsapp.data.util.Resource

interface BreedRepository {
    suspend fun getBreedList(): Resource<List<Breed>>
}