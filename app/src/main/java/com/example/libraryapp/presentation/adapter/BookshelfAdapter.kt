package com.example.libraryapp.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.libraryapp.databinding.BookshelfItemBinding
import com.example.libraryapp.domain.model.Bookshelf
import com.example.libraryapp.presentation.adapter.viewholder.BookshelfViewHolder

class BookshelfAdapter(
    private val onItemClick: (Bookshelf) -> Unit
) : ListAdapter<Pair<Bookshelf, Int>, BookshelfViewHolder>(DiffCallback) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookshelfViewHolder =
        BookshelfViewHolder(
            BookshelfItemBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )

    override fun onBindViewHolder(holder: BookshelfViewHolder, position: Int) {
        val current = getItem(position)
        holder.bind(current.first)
        holder.itemView.setOnClickListener {
            onItemClick(current.first)
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Pair<Bookshelf, Int>>() {
        override fun areItemsTheSame(
            oldItem: Pair<Bookshelf, Int>,
            newItem: Pair<Bookshelf, Int>
        ): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(
            oldItem: Pair<Bookshelf, Int>,
            newItem: Pair<Bookshelf, Int>
        ): Boolean {
            return oldItem.first.bookshelfTitle == newItem.first.bookshelfTitle
        }

    }
}