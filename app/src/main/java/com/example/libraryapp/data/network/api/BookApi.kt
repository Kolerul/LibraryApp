package com.example.libraryapp.data.network.api

import com.example.libraryapp.data.network.model.BookResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface BookApi {

    @GET("volumes")
    suspend fun getVolumes(
        @Query("q") intelligence: String,
        @Query("intitle") title: String = "",
        @Query("inauthor") author: String = "",
        @Query("subject") category: String = "",
        @Query("startIndex") startIndex: Int = 0,
        @Query("printType") printType: String = "all",
        @Query("orderBy") orderBy: String = "relevance",
        @Query("key") apiKey: String
    ): BookResponse


    companion object{
        const val BASE_URL = "https://www.googleapis.com/books/v1/"
    }
}