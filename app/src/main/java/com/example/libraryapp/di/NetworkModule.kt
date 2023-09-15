package com.example.libraryapp.di


import com.example.libraryapp.data.network.api.BookApi
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

@Module
class NetworkModule {

    @Provides
    fun provideGson(): Gson =
        GsonBuilder()
            .setLenient()
            .create()

    @Provides
    fun provideGsonFactory(gson: Gson): GsonConverterFactory =
        GsonConverterFactory
            .create(gson)

    @Provides
    fun provideClient(): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(
                HttpLoggingInterceptor()
                    .setLevel(HttpLoggingInterceptor.Level.BASIC))
            .build()

    @Provides
    fun provideRetrofit(factory: GsonConverterFactory, client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(BookApi.BASE_URL)
            .client(client)
            .addConverterFactory(factory)
            .build()

    @Provides
    fun provideService(retrofit: Retrofit): BookApi =
        retrofit.create()
}