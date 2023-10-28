package com.example.libraryapp.ui.fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.libraryapp.LibraryApp
import com.example.libraryapp.databinding.FragmentSearchBinding
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.presentation.adapter.BookAdapter
import com.example.libraryapp.presentation.uistate.SearchUIState
import com.example.libraryapp.presentation.viewmodel.SearchViewModel
import com.google.android.material.snackbar.Snackbar

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by viewModels {
        (requireActivity().application as LibraryApp).appComponent.viewModelsFactory()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUIStateObserver()
        setOnClickListeners()
    }

    private fun setOnClickListeners() {
        binding.apply {
            searchButton.setOnClickListener {
                if (titleSearchField.text.isNotEmpty() || authorSearchField.text.isNotEmpty())
                    viewModel.searchBooksByTitleAndAuthor(
                        titleSearchField.text.toString(),
                        authorSearchField.text.toString()
                    )
                else
                    showSnackBar("No information found")
            }
        }
    }

    private fun setUIStateObserver() {
        viewModel.uiState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is SearchUIState.Initialization -> {
                    binding.apply {
                        progressBar.visibility = View.GONE
                        contentLayout.visibility = View.VISIBLE
                    }
                }

                is SearchUIState.Loading -> {
                    binding.apply {
                        progressBar.visibility = View.VISIBLE
                        contentLayout.visibility = View.GONE
                    }
                }

                is SearchUIState.Success -> {
                    binding.apply {
                        progressBar.visibility = View.GONE
                        contentLayout.visibility = View.VISIBLE
                    }
                    setAdapter(state.dataList)
                }

                is SearchUIState.Error -> {
                    binding.apply {
                        progressBar.visibility = View.GONE
                        contentLayout.visibility = View.VISIBLE
                    }
                    showSnackBar("Error ${state.errorType}: ${state.message}")
                }
            }
        }
    }

    private fun setAdapter(list: List<Book>) {
        val adapter = BookAdapter(
            viewModel::addBookToBookshelf
        )
        binding.bookRecyclerView.adapter = adapter
        adapter.submitList(list)
    }

    private fun showSnackBar(message: String) {
        val snackBar = Snackbar.make(
            binding.contentLayout,
            message,
            3000
        )

        snackBar.show()
    }
}