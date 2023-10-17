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
                R.string.categories_info,
                book.categories.joinToString(
                    separator = ", "
                )
            )

            previewImage.load({ "https://ru.m.wikipedia.org/wiki/Файл:${book.title}.jpg" }) {
                placeholder(R.drawable.loading_animation)
                error(randomizeImage())
            }

            starIcon.setOnClickListener { _ ->
                onStarClickListener(book, "Favourite")
                starIcon.setImageResource(R.drawable.ic_full_star)
            }
        }
    }

    private fun randomizeImage(): Int {
        val random = Math.random() * 6
        if (random < 1) return R.drawable.red_book
        if (random < 2) return R.drawable.blue_book
        if (random < 3) return R.drawable.green_book
        if (random < 4) return R.drawable.yellow_book
        if (random < 5) return R.drawable.orange_book
        return R.drawable.violet_book
    }
}