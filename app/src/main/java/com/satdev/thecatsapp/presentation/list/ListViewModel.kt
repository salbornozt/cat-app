package com.satdev.thecatsapp.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.satdev.thecatsapp.data.model.Breed
import com.satdev.thecatsapp.data.util.Resource
import com.satdev.thecatsapp.domain.repository.BreedRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val breedRepository: BreedRepository) : ViewModel() {
    private val _catsLiveData : MutableLiveData<Resource<List<Breed>>> by lazy {
        MutableLiveData<Resource<List<Breed>>>().apply {
            getCatsList()
        }
    }

    val catsLiveData : LiveData<Resource<List<Breed>>> get() = _catsLiveData

    private fun getCatsList() = viewModelScope.launch(Dispatchers.Main) {
        _catsLiveData.value = Resource.Loading()
        _catsLiveData.value = withContext(Dispatchers.IO) {
            breedRepository.getBreedList()
        }
    }
}