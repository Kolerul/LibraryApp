package com.example.libraryapp.presentation.uistate

import com.example.libraryapp.domain.model.Bookshelf
import kotlinx.coroutines.flow.Flow

sealed class BookshelfListUiState {
    data object Initializing : BookshelfListUiState()
    data object Loading : BookshelfListUiState()
    data class Success(val bookshelves: Flow<List<Pair<Bookshelf, Int>>>) : BookshelfListUiState()
    data class Error(val errorType: String, val message: String) : BookshelfListUiState()
}