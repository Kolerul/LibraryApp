package com.example.libraryapp.di
import com.example.libraryapp.data.repository.BookRepositoryImpl
import com.example.libraryapp.domain.repository.BookRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    fun bindBookRepository(repository: BookRepositoryImpl): BookRepository
}