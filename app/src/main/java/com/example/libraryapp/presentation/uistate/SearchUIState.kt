package com.example.libraryapp.presentation.uistate

import com.example.libraryapp.domain.entity.Book

sealed class SearchUIState {
    object Initialization : SearchUIState()
    object Loading : SearchUIState()
    data class Success(val dataList: List<Book>) : SearchUIState()
    data class Error(val errorType: String, val message: String) : SearchUIState()
}