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
            author.text = root.context.getString(
                R.string.authors_info,
                book.authors.joinToString(
                    separator = ", "
                )
            )

            publishedDate.text = root.context.getString(
                R.string.publish_date_info,
                book.publishedDate
            )

            publisher.text = root.context.getString(
                R.string.publisher_info,
                book.publisher
            )

            categories.text = root.context.getString(
                R.string.publisher_info,
                book.categories.joinToString(
                    separator = ", "
                )
            )

            previewImage.load({ "https://ru.m.wikipedia.org/wiki/Файл:${book.title}.jpg" }) {
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