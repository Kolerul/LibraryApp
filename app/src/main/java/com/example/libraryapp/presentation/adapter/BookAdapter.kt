package com.example.libraryapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.libraryapp.databinding.BookItemBinding
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.presentation.adapter.viewholder.BookViewHolder

class BookAdapter(
    private val onStarClickListener: (book: Book, bookshelf: String) -> Unit
) : ListAdapter<Book, BookViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookViewHolder =
        BookViewHolder(
            BookItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            ),
            onStarClickListener
        )

    override fun onBindViewHolder(holder: BookViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Book>() {

        override fun areItemsTheSame(oldItem: Book, newItem: Book): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Book, newItem: Book): Boolean {
            return (oldItem.title == newItem.title && oldItem.publisher == newItem.publisher)
        }
    }
}