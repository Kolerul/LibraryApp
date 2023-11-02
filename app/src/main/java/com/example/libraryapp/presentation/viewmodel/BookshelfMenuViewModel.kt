package com.example.libraryapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.libraryapp.domain.model.Bookshelf
import com.example.libraryapp.domain.repository.BookshelfRepository
import javax.inject.Inject

class BookshelfMenuViewModel @Inject constructor(
    private val repository: BookshelfRepository
) : ViewModel() {

    private val _selectedBookshelf = MutableLiveData<Bookshelf>()
    val selectedBookshelf: LiveData<Bookshelf>
        get() = _selectedBookshelf

    private val _bookshelfList = repository.getAllBookshelvesWithNumberOfBooks()
    val bookshelfList: LiveData<List<Pair<Bookshelf, Int>>>
        get() = _bookshelfList.asLiveData()

    //fun getAllBookshelves(): Flow<List<Pair<Bookshelf, Int>>> = repository.getAllBookshelvesWithNumberOfBooks()

    fun selectBookshelf(bookshelf: Bookshelf) {
        _selectedBookshelf.value = bookshelf
    }
}