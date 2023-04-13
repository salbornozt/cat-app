package com.satdev.thecatsapp.data.repository

import com.satdev.thecatsapp.data.model.Breed
import com.satdev.thecatsapp.data.repository.dataSource.BreedRemoteDataSource
import com.satdev.thecatsapp.data.util.Resource
import com.satdev.thecatsapp.domain.repository.BreedRepository
import javax.inject.Inject

class BreedRepositoryImpl @Inject constructor(private val breedRemoteDataSource: BreedRemoteDataSource) : BreedRepository {
    override suspend fun getBreedList(): Resource<List<Breed>> {
        return getBreedsFromWebService()
    }

    private suspend fun getBreedsFromWebService(): Resource<List<Breed>> {
        lateinit var breedList: List<Breed>
        try {
            val response = breedRemoteDataSource.getBreed()
            val body = response.body()
            if (body != null){
                breedList = body
            }
        }catch (e:Exception){
            e.printStackTrace()
            return Resource.Error(message = e.message ?: "Ocurrió un error al traer los resultados")
        }
        return Resource.Success(breedList)
    }
}