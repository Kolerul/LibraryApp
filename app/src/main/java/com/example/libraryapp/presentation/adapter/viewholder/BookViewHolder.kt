package com.example.libraryapp.presentation.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapp.databinding.BookItemBinding
import com.example.libraryapp.domain.entity.Book

class BookViewHolder(private val binding: BookItemBinding) : RecyclerView.ViewHolder(binding.root) {

    fun bind(book: Book) {
        binding.apply {
            title.text = book.title
            author.text = book.authors.joinToString(
                separator = ", "
            )
            publishedDate.text = book.publishedDate
            publisher.text = book.publisher
            categories.text = book.categories.joinToString(
                separator = ", "
            )
        }
    }
}