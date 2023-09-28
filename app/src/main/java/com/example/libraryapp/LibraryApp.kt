package com.example.libraryapp

import android.app.Application
import com.example.libraryapp.di.AppComponent
import com.example.libraryapp.di.DaggerAppComponent

class LibraryApp : Application() {

    val appComponent: AppComponent by lazy {
        DaggerAppComponent.factory().create(this)

    }
}