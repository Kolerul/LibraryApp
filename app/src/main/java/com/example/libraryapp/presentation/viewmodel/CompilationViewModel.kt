package com.example.libraryapp.presentation.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.domain.model.PrintTypes
import com.example.libraryapp.domain.repository.CompilationRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class CompilationViewModel @Inject constructor(
    private val repository: CompilationRepository
) : ViewModel() {

    private val _booksList = MutableLiveData<List<Book>>(mutableListOf())
    val bookList: LiveData<List<Book>>
        get() = _booksList

    private val _magazineList = MutableLiveData<List<Book>>(listOf())
    val magazineList: LiveData<List<Book>>
        get() = _magazineList

    private val _categoryMap = HashMap<String, MutableLiveData<List<Book>>>()
    val categoryMap: HashMap<String, MutableLiveData<List<Book>>>
        get() = _categoryMap

    private val _categoryList = MutableLiveData<List<String>>(emptyList())
    val categoryList: LiveData<List<String>>
        get() = _categoryList

    private var newest = MutableLiveData(false)


    fun getBooks(category: String, startIndex: Int, isNewest: Boolean) {
        getBooksByCategory(category, startIndex, isNewest, PrintTypes.ALL)
    }


    private fun getBooksByCategory(
        category: String,
        startIndex: Int,
        isNewest: Boolean,
        printType: PrintTypes
    ) {
        viewModelScope.launch {
            _categoryMap[category]!!.value =
                repository.getBooksByCategory(category, startIndex, isNewest, printType)
        }
    }

    fun getCategories() {
        viewModelScope.launch {
            val list = repository.getCategories()
            for (category in list) {
                _categoryMap[category] = MutableLiveData(emptyList())
            }
            _categoryList.value = list
        }
    }
}