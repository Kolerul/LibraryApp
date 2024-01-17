package com.example.libraryapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import coil.load
import com.example.libraryapp.LibraryApp
import com.example.libraryapp.R
import com.example.libraryapp.databinding.FragmentDetailsBinding
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.presentation.uistate.DetailsUIState
import com.example.libraryapp.presentation.viewmodel.DetailsViewModel
import com.google.android.material.snackbar.Snackbar

class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    private val viewModel: DetailsViewModel by viewModels {
        (requireActivity().application as LibraryApp).appComponent.viewModelsFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bookId: String = arguments?.getString(ID_KEY) ?: ""
        val bookshelfTitle: String = arguments?.getString(BOOKSHELF_KEY) ?: ""

        setUIStateObserver(bookId, bookshelfTitle)
        setOnClickListeners(bookshelfTitle)
    }

    private fun setOnClickListeners(bookshelfTitle: String) {
        binding.apply {
            deleteIcon.setOnClickListener {
                if (viewModel.uiState.value is DetailsUIState.Success) {
                    viewModel.deleteBook(
                        (viewModel.uiState.value as DetailsUIState.Success).book.id,
                        bookshelfTitle
                    )
                }
            }
        }
    }

    private fun setUIStateObserver(bookId: String, bookshelfTitle: String) {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DetailsUIState.Initialization -> {
                    binding.apply {
                        contentLayout.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    }


                    viewModel.getBook(bookId, bookshelfTitle)
                }

                is DetailsUIState.Loading -> {
                    binding.apply {
                        contentLayout.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
                    }
                }

                is DetailsUIState.Success -> {
                    binding.apply {
                        contentLayout.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                        setBookDetails(state.book)
                    }
                }

                is DetailsUIState.Error -> {
                    showSnackBar("Error ${state.errorType}: ${state.message}")
                }

                else -> {}
            }
        }
    }

    private fun setBookDetails(book: Book) {
        binding.apply {
            title.text = book.title
            authors.text = getString(
                R.string.authors_info, book.authors.joinToString(
                    separator = ", "
                )
            )
            categories.text = getString(
                R.string.categories_info, book.categories.joinToString(
                    separator = ", "
                )
            )
            publishedDate.text = getString(
                R.string.publish_date_info, book.publishedDate
            )
            publisher.text = getString(
                R.string.publisher_info, book.publisher
            )
            description.text = book.description

            bookImage.load(book.imageUrl.replace("http", "https")) {
                crossfade(true)
                placeholder(R.drawable.loading_animation)
                error(R.drawable.ic_image_not_found)
            }

            background.load(book.imageUrl.replace("http", "https")) {
                crossfade(true)
                placeholder(R.drawable.loading_animation)
            }
        }
    }


    private fun showSnackBar(message: String) {
        val snackbar = Snackbar.make(binding.contentLayout, message, 3000)
        snackbar.show()
    }

    companion object {
        const val ID_KEY = "id"
        const val BOOKSHELF_KEY = "bookshelf"
    }
}