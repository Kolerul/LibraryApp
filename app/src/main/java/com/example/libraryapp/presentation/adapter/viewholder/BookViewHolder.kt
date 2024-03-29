package com.example.libraryapp.presentation.adapter.viewholder


import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.libraryapp.R
import com.example.libraryapp.databinding.BookItemBinding
import com.example.libraryapp.domain.model.Book


class BookViewHolder(
    private val binding: BookItemBinding,
    private val onMoreClickListener: (Book) -> Unit
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



            previewImage.load(book.imageUrl.replace("http", "https")) {
                crossfade(true)
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_image_not_found)
            }

            Log.d("ViewHolder", book.imageUrl)

            moreIcon.setOnClickListener {
                onMoreClickListener(book)
            }
        }
    }
}
