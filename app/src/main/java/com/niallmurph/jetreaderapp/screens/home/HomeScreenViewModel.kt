package com.niallmurph.jetreaderapp.screens.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.niallmurph.jetreaderapp.data.DataOrException
import com.niallmurph.jetreaderapp.models.MBook
import com.niallmurph.jetreaderapp.repository.FireRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(private val repository: FireRepository) :
    ViewModel() {

    val data: MutableState<DataOrException<List<MBook>, Boolean, Exception>> = mutableStateOf(
        DataOrException(listOf(), true, Exception(""))
    )

    init{
        getAllBooksFromDatabase()
    }

    fun getAllBooksFromDatabase() {
        viewModelScope.launch {
            data.value.loading = true
            data.value = repository.getAllBooksFromDatabase()
            Log.d("FIRE_SEARCH","HomeScreenViewModel - getAllBooksFromDatabase result : ${data.value.data}")
            if(!data.value.data.isNullOrEmpty()) data.value.loading = false
        }
    }

}