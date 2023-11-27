package com.example.libraryapp.ui.fragments


import android.os.Bundle
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.example.libraryapp.LibraryApp
import com.example.libraryapp.databinding.FragmentSearchBinding
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.presentation.adapter.BookAdapter
import com.example.libraryapp.presentation.uistate.SearchUIState
import com.example.libraryapp.presentation.viewmodel.BookViewModel
import com.example.libraryapp.presentation.viewmodel.SearchViewModel
import com.google.android.material.snackbar.Snackbar
import javax.inject.Inject

class SearchFragment : BaseFragment<FragmentSearchBinding>(FragmentSearchBinding::inflate) {

    private val viewModel: SearchViewModel by viewModels {
        (requireActivity().application as LibraryApp).appComponent.viewModelsFactory()
    }

    private val bookViewModel: BookViewModel by activityViewModels {
        (requireActivity().application as LibraryApp).appComponent.viewModelsFactory()
    }

    @Inject
    lateinit var menuDialog: BottomSheetMenuDialogFragment

    @Inject
    lateinit var bookshelfMenuDialog: BottomSheetBookshelfMenuDialogFragment

    @Inject
    lateinit var createBookshelfDialog: CreateBookshelfDialogFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (requireActivity().application as LibraryApp).appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUIStateObserver()
        setOnClickListeners()
        setBottomSheetMenuListener()
        setAddTooBookshelfListener()
        setCreateBookshelfDialogListener()
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


    private fun setBottomSheetMenuListener() {
        setFragmentResultListener(BottomSheetMenuDialogFragment.REQUEST_KEY) { _, bundle ->
            val result = bundle.getInt(BottomSheetMenuDialogFragment.MENU_ITEM_KEY)
            when (result) {
                1 -> {

                    val book = bookViewModel.book.value
                    book?.let {
                        bookViewModel.addBookToBookshelf(book, "Favourite")
                    }
                }

                2 -> {
                    showBottomSheetBookshelfMenu()
                }

                3 -> {
                    showCreateBookshelfDialog()
                }
            }
            menuDialog.dismiss()
        }
    }

    private fun showCreateBookshelfDialog() {
        createBookshelfDialog.show(parentFragmentManager, CreateBookshelfDialogFragment.TAG)
    }

    private fun setCreateBookshelfDialogListener() {
        setFragmentResultListener(CreateBookshelfDialogFragment.REQUEST_KEY) { _, bundle ->
            val bookshelfTitle = bundle.getString(CreateBookshelfDialogFragment.RESPONSE_KEY)
            val book = bookViewModel.book.value
            if (bookshelfTitle != null && book != null) {
                bookViewModel.addBookToBookshelf(book, bookshelfTitle)
            }
        }
    }

    private fun setAddTooBookshelfListener() {
        setFragmentResultListener(BottomSheetBookshelfMenuDialogFragment.REQUEST_KEY) { _, bundle ->
            val bookshelfTitle =
                bundle.getString(BottomSheetBookshelfMenuDialogFragment.RESPONSE_KEY)
            val book = bookViewModel.book.value
            if (bookshelfTitle != null && book != null) {
                bookViewModel.addBookToBookshelf(book, bookshelfTitle)
            }
        }
    }

    private fun showBottomSheetMenu(book: Book) {
        bookViewModel.selectBook(book)
        menuDialog.show(parentFragmentManager, BottomSheetMenuDialogFragment.TAG)
    }

    private fun showBottomSheetBookshelfMenu() {
        bookshelfMenuDialog.show(parentFragmentManager, BottomSheetBookshelfMenuDialogFragment.TAG)
    }

}