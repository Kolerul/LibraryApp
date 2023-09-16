package com.example.libraryapp.di
import com.example.libraryapp.data.repository.BookRepositoryImpl
import com.example.libraryapp.domain.repository.BookRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DomainModule {

    @Singleton
    @Binds
    fun bindBookRepository(repository: BookRepositoryImpl): BookRepository
}