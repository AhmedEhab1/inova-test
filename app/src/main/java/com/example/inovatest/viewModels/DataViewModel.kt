package com.example.inovatest.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.inovatest.domain.DataModel
import com.example.inovatest.domain.DataRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DataViewModel @Inject constructor(private val repository: DataRepository) : ViewModel() {
    private val _data: MutableLiveData<DataModel?> = MutableLiveData()
    val dataModel: LiveData<DataModel?> = _data
    private val _errorMessage: MutableLiveData<String?> = MutableLiveData()
    val errorMessage: LiveData<String?> = _errorMessage

    fun fetchData() {
        viewModelScope.launch {
            try {
                val posts = withContext(Dispatchers.IO) {
                    repository.fetchData()
                }
                _data.value = posts
            } catch (e: Exception) {
                _errorMessage.value = e.message
            }
        }
    }
}
