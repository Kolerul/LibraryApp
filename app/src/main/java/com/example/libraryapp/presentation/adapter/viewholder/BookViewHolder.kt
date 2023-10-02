package com.example.libraryapp.presentation.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.libraryapp.R
import com.example.libraryapp.databinding.BookItemBinding
import com.example.libraryapp.domain.entity.Book

class BookViewHolder(
    private val binding: BookItemBinding,
    private val onStarClickListener: (book: Book, bookshelf: String) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

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
            previewImage.load({ book.imageUrl }) {
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_image_not_found)
            }

            starIcon.setOnClickListener { _ ->
                onStarClickListener(book, "Favourite")
                starIcon.setImageResource(R.drawable.ic_full_star)
            }
        }
    }
}