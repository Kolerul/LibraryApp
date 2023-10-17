package com.example.libraryapp.ui.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.libraryapp.LibraryApp
import com.example.libraryapp.R
import com.example.libraryapp.databinding.FragmentDetailsBinding
import com.example.libraryapp.domain.entity.Book
import com.example.libraryapp.presentation.uistate.DetailsUIState
import com.example.libraryapp.presentation.viewmodel.DetailsViewModel
import com.google.android.material.snackbar.Snackbar

class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    private val viewModel: DetailsViewModel by viewModels {
        (requireActivity().application as LibraryApp).appComponent.viewModelsFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUIStateObserver()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.apply {
            deleteIcon.setOnClickListener {
                if (viewModel.uiState.value is DetailsUIState.Success) {
                    viewModel.deleteBook(
                        (viewModel.uiState.value as DetailsUIState.Success).book.id,
                        "Favourite"
                    )
                }
            }
        }
    }

    private fun setUIStateObserver() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is DetailsUIState.Initialization -> {
                    binding.apply {
                        contentLayout.visibility = View.VISIBLE
                        progressBar.visibility = View.GONE
                    }
                    val id = arguments?.getString(ID_KEY) ?: ""
                    viewModel.getBook(id, "Favourite")
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
        }
    }


    private fun showSnackBar(message: String) {
        val snackbar = Snackbar.make(binding.contentLayout, message, 3000)
        snackbar.show()
    }

    companion object {
        const val ID_KEY = "id"
    }
}