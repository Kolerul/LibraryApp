package com.example.libraryapp.presentation.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.example.libraryapp.databinding.BookshelfItemBinding
import com.example.libraryapp.domain.model.Bookshelf

class BookshelfViewHolder(
    private val binding: BookshelfItemBinding
) : RecyclerView.ViewHolder(binding.root) {
    fun bind(bookshelf: Bookshelf) {
        binding.title.text = bookshelf.bookshelfTitle
    }
}