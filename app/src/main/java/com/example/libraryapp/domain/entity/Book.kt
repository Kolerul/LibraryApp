package com.example.libraryapp.domain.entity

data class Book(
    val title: String,
    val authors: List<String>,
    val id: String,
    val publishedDate: String,
    val description: String,
    val publisher: String,
    val imageUrl: String,
    val categories: List<String>
)