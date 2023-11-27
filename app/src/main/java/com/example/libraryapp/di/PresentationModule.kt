package com.example.libraryapp.di

import androidx.lifecycle.ViewModel
import com.example.libraryapp.presentation.viewmodel.BookshelfViewModel
import com.example.libraryapp.presentation.viewmodel.DetailsViewModel
import com.example.libraryapp.presentation.viewmodel.BookViewModel
import com.example.libraryapp.presentation.viewmodel.BookshelfMenuViewModel
import com.example.libraryapp.presentation.viewmodel.SearchViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
interface PresentationModule {

    @Binds
    @IntoMap
    @ViewModelKey(SearchViewModel::class)
    fun bindSearchViewModel(viewModel: SearchViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    fun bindDetailsViewModel(viewModel: DetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(BookshelfViewModel::class)
    fun bindBookshelfViewModel(viewModel: BookshelfViewModel): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(BookViewModel::class)
    fun bindMenuViewModel(viewModel: BookViewModel): ViewModel

    @Singleton
    @Binds
    @IntoMap
    @ViewModelKey(BookshelfMenuViewModel::class)
    fun bindBookshelfMenuViewModel(viewModel: BookshelfMenuViewModel): ViewModel
}