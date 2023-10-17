package com.example.libraryapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.example.libraryapp.R
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.libraryapp.LibraryApp
import com.example.libraryapp.presentation.uistate.BookshelfUIState
import com.example.libraryapp.presentation.viewmodel.BookshelfViewModel
import com.example.libraryapp.ui.compose.Bookshelf
import com.example.libraryapp.ui.compose.BookshelfTopAppBar
import com.example.libraryapp.ui.compose.CircularProgressBar


class BookshelfFragment : Fragment() {

    private val viewModel: BookshelfViewModel by viewModels {
        (requireActivity().application as LibraryApp).appComponent.viewModelsFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getBooksFromBookshelf("Favourite")
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    UIStateObserver()
                }
            }
        }
    }

    @Composable
    private fun UIStateObserver() {
        val state = viewModel.booksUIState.observeAsState()
        when (state.value) {
            is BookshelfUIState.Initializing -> {

                viewModel.getBooksFromBookshelf("Favourite")
            }

            is BookshelfUIState.Loading -> {
                CircularProgressBar()
            }

            is BookshelfUIState.Success -> {
                BookshelfTopAppBar(
                    { viewModel.createBookshelf("Favourite") }
                ) {
                    Bookshelf(
                        (state.value as BookshelfUIState.Success).books,
                        { id ->
                            val bundle = bundleOf(DetailsFragment.ID_KEY to id)
                            findNavController().navigate(
                                R.id.action_bookshelfFragment_to_detailsFragment,
                                bundle
                            )
                        }
                    )
                }
            }

            is BookshelfUIState.Error -> {}
            else -> {}
        }
    }
}