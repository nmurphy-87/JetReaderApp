package com.niallmurph.jetreaderapp.screens.details

import androidx.lifecycle.ViewModel
import com.niallmurph.jetreaderapp.repository.BookRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import com.niallmurph.jetreaderapp.models.Item
import com.niallmurph.jetreaderapp.data.Resource

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: BookRepository) : ViewModel() {

    suspend fun getBookInfo(bookId: String): Resource<Item> {
        return repository.getBookInfo(bookId = bookId)
    }
}