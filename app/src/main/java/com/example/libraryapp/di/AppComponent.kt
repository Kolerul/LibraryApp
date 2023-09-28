package com.example.libraryapp.di

import android.content.Context
import com.example.libraryapp.presentation.viewmodel.ViewModelFactory
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [
    NetworkModule::class,
    DomainModule::class,
    CoroutineModule::class,
    DatabaseModule::class,
    PresentationModule::class
])
interface AppComponent {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }

    fun viewModelsFactory(): ViewModelFactory
}