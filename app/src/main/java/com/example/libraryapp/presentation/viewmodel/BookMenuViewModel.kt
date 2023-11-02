package com.example.libraryapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.ui.model.MenuItem
import javax.inject.Inject

class BookMenuViewModel @Inject constructor() : ViewModel() {

    private val _menu: MutableList<MenuItem> = mutableListOf()

    val menu: List<MenuItem>
        get() = _menu.toList()

    private val _book = MutableLiveData<Book>()
    val book: LiveData<Book>
        get() = _book

    private val addedMenuItems: MutableSet<String> = mutableSetOf()


    fun addMenuItem(item: MenuItem, index: Int = _menu.size) {
        if (!addedMenuItems.contains(item.title)) {
            _menu.add(index, item)
            addedMenuItems.add(item.title)
        }
    }

    fun selectBook(book: Book) {
        _book.value = book
    }
}