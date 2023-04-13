package com.satdev.thecatsapp.data.repository.dataSourceImpl

import com.google.common.base.Verify.verify
import com.google.common.truth.Truth.assertThat
import com.satdev.thecatsapp.data.api.ApiService
import com.satdev.thecatsapp.data.model.Breed
import kotlinx.coroutines.runBlocking
import okhttp3.MediaType
import okhttp3.ResponseBody
import org.junit.Before
import org.junit.Test
import org.mockito.kotlin.doThrow
import org.mockito.kotlin.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class BreedRemoteDataSourceImplTest {
    private lateinit var SUT : BreedRemoteDataSourceImpl
    private lateinit var api : ApiService
    companion object {
        val emptyList : Response<List<Breed>> = Response.success(emptyList())
        val serverError : Response<List<Breed>> = Response.error(500, ResponseBody.create(MediaType.get("application/json; charset=utf-8"),""))
    }
    @Before
    fun setUp() {
        api = mock {  }
        SUT = BreedRemoteDataSourceImpl(api)
    }

    @Test(expected = Exception::class)
    fun getBreeds_throwsException() {
        runBlocking {
            //Arrange
            getListExceptionError()
            //Act
            SUT.getBreed()
            //Assert
        }
    }

    @Test()
    fun getBreeds_returnsEmptyList() {
        runBlocking {
            //Arrange
            getListEmptyList()
            //Act
            val result = SUT.getBreed()
            val expected = emptyList
            //Assert
            assertThat(result).isEqualTo(expected)
        }
    }

    @Test()
    fun getBreeds_returnsServerError() {
        runBlocking {
            //Arrange
            getListServerError()
            //Act
            val result = SUT.getBreed()
            val expected = serverError
            //Assert
            assertThat(result.code()).isEqualTo(expected.code())
        }
    }

    private suspend fun getListServerError() {
        whenever(api.getAllBreeds()).thenReturn(serverError)
    }

    private suspend fun getListEmptyList() {
        whenever(api.getAllBreeds()).thenReturn(emptyList)
    }

    private suspend fun getListExceptionError() {
        doThrow(Exception()).`when`(api).getAllBreeds()
    }
}