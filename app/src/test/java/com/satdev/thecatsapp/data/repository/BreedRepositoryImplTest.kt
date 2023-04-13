package com.satdev.thecatsapp.data.repository

import com.google.common.truth.Truth.assertThat
import com.satdev.thecatsapp.data.model.Breed
import com.satdev.thecatsapp.data.repository.dataSource.BreedRemoteDataSource
import com.satdev.thecatsapp.data.repository.dataSourceImpl.BreedRemoteDataSourceImpl
import com.satdev.thecatsapp.data.util.Resource
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class BreedRepositoryImplTest {

    private lateinit var SUT : BreedRepositoryImpl
    private lateinit var breedRemoteDataSource: BreedRemoteDataSource

    companion object {
        val LIST : Response<List<Breed>> = Response.success(emptyList())
    }

    @Before
    fun setUp() {
        breedRemoteDataSource = mock {  }
        SUT =  BreedRepositoryImpl(breedRemoteDataSource)
    }

    @Test
    fun getBreeds_remote_service_return_error() = runBlocking{
        //Arrange
        getErrorFromRemoteService()
        //Act
        val result = SUT.getBreedList()
        val expected= Resource.Error<List<Breed>>(RuntimeException("Error").message!!)
        //Assert
        assertThat(result).isInstanceOf(Resource.Error::class.java)
        assertThat(result.message).isEqualTo(expected.message)
    }

    @Test
    fun getBreeds_remote_service_return_success() = runBlocking{
        //Arrange
        getSuccessFromRemoteService()
        //Act
        val result = SUT.getBreedList()
        val expected= Resource.Success<List<Breed>>(listOf())
        //Assert
        assertThat(result).isInstanceOf(Resource.Success::class.java)


    }

    private suspend fun getSuccessFromRemoteService() {
        whenever(breedRemoteDataSource.getBreed()).thenReturn(LIST)
    }

    private suspend fun getErrorFromRemoteService() {
        doThrow(RuntimeException("Error")).`when`(breedRemoteDataSource).getBreed()
    }
}