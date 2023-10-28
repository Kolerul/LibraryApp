package com.example.libraryapp.presentation.uistate

import com.example.libraryapp.domain.model.Book

sealed class SearchUIState {
    data object Initialization : SearchUIState()
    data object Loading : SearchUIState()
    data class Success(val dataList: List<Book>) : SearchUIState()
    data class Error(val errorType: String, val message: String) : SearchUIState()
}