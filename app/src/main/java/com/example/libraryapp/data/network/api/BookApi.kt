package com.example.libraryapp.data.network.api

import com.example.libraryapp.data.network.model.BookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {

    @GET("volumes")
    suspend fun getVolumes(
        @Query("q") intelligence: String,
        @Query("key") apiKey: String
    ): BookResponse


    companion object{
        const val BASE_URL = "https://www.googleapis.com/books/v1/"
    }
}