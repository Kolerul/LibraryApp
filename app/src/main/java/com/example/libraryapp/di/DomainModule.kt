package com.example.libraryapp.di
import com.example.libraryapp.data.repository.BookRepositoryImpl
import com.example.libraryapp.data.repository.BookshelfRepositoryImpl
import com.example.libraryapp.data.repository.CompilationRepositoryImpl
import com.example.libraryapp.domain.repository.BookRepository
import com.example.libraryapp.domain.repository.BookshelfRepository
import com.example.libraryapp.domain.repository.CompilationRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    fun bindBookRepository(repository: BookRepositoryImpl): BookRepository

    @Binds
    fun bindBookshelfRepository(repository: BookshelfRepositoryImpl): BookshelfRepository

    @Binds
    fun bindCompilationRepository(repository: CompilationRepositoryImpl): CompilationRepository
}