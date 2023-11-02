package com.example.libraryapp.ui.fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import com.example.libraryapp.LibraryApp
import com.example.libraryapp.R
import com.example.libraryapp.databinding.FragmentSearchBinding
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.presentation.adapter.BookAdapter
import com.example.libraryapp.presentation.uistate.SearchUIState
import com.example.libraryapp.presentation.viewmodel.BookMenuViewModel
import com.example.libraryapp.presentation.viewmodel.BookshelfMenuViewModel
import com.example.libraryapp.presentation.viewmodel.SearchViewModel
import com.example.libraryapp.ui.model.MenuItem
import com.google.android.material.snackbar.Snackbar

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by viewModels {
        (requireActivity().application as LibraryApp).appComponent.viewModelsFactory()
    }

    private val bookMenuViewModel: BookMenuViewModel by viewModels {
        (requireActivity().application as LibraryApp).appComponent.viewModelsFactory()
    }

    private val bookshelfMenuViewModel: BookshelfMenuViewModel by viewModels {
        (requireActivity().application as LibraryApp).appComponent.viewModelsFactory()
    }

    private val menuDialog = BottomSheetMenuDialogFragment()

    private val bookshelfMenuDialog = BottomSheetBookshelfMenuDialogFragment()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUIStateObserver()
        setOnClickListeners()
        createBottomSheetMenu()
        setSelectedBookshelfObserver()
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

    private fun setSelectedBookshelfObserver() {
        bookshelfMenuViewModel.selectedBookshelf.observe(viewLifecycleOwner) { bookshelf ->
            val book = bookMenuViewModel.book.value
            book?.let {
                viewModel.addBookToBookshelf(it, bookshelf.bookshelfTitle)
            }
        }
    }

    private fun setAdapter(list: List<Book>) {
        val adapter = BookAdapter { book ->
            showBottomSheetMenu(book)
        }
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

    private fun createBottomSheetMenu() {
        val menuItemFavourite = MenuItem(R.drawable.ic_favourite, "Add to favourite") {
            val book = bookMenuViewModel.book.value
            book?.let {
                viewModel.addBookToBookshelf(book, "Favourite")
            }
        }
        val menuItemAddToBookshelf = MenuItem(R.drawable.ic_add, "Add to bookshelf") {
            showBottomSheetBookshelfMenu()
        }
        val menuItemCreateBookshelf = MenuItem(R.drawable.ic_create, "Create bookshelf") { }
        bookMenuViewModel.addMenuItem(menuItemFavourite)
        bookMenuViewModel.addMenuItem(menuItemAddToBookshelf)
        bookMenuViewModel.addMenuItem(menuItemCreateBookshelf)
    }

    private fun showBottomSheetMenu(book: Book) {
        bookMenuViewModel.selectBook(book)
        menuDialog.show(parentFragmentManager, BottomSheetMenuDialogFragment.TAG)
    }

    private fun showBottomSheetBookshelfMenu() {
        bookshelfMenuDialog.show(parentFragmentManager, BottomSheetBookshelfMenuDialogFragment.TAG)
    }

}