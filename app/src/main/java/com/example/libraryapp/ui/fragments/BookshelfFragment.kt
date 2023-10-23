package com.example.libraryapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import com.example.libraryapp.R
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.libraryapp.LibraryApp
import com.example.libraryapp.domain.entity.Bookshelf
import com.example.libraryapp.presentation.uistate.BookshelfListUiState
import com.example.libraryapp.presentation.uistate.BookshelfUIState
import com.example.libraryapp.presentation.viewmodel.BookshelfViewModel
import com.example.libraryapp.ui.compose.item.AppBarItem
import com.example.libraryapp.ui.compose.Bookshelf
import com.example.libraryapp.ui.compose.CenterCircularProgressBar
import com.example.libraryapp.ui.compose.item.DrawerItem
import com.example.libraryapp.ui.compose.NavigationDrawer
import com.example.libraryapp.ui.compose.TopAppBar
import kotlinx.coroutines.launch


class BookshelfFragment : Fragment() {

    private val viewModel: BookshelfViewModel by viewModels {
        (requireActivity().application as LibraryApp).appComponent.viewModelsFactory()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {
                    BookshelfFragmentMainScreen()
                }
            }
        }
    }

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun BookshelfFragmentMainScreen(
        modifier: Modifier = Modifier
    ) {
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val scrollBehavior =
            TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
        var currentBookshelf by rememberSaveable { mutableStateOf("") }


        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    val bookshelfState = viewModel.bookshelfUIState.observeAsState()
                    BookshelfListObserver(state = bookshelfState.value!!) { newBookshelf ->
                        currentBookshelf = newBookshelf.bookshelfTitle
                        viewModel.getBooksFromBookshelf(Bookshelf(currentBookshelf))
                    }
                }
            }
        ) {
            Scaffold(
                modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    TopAppBar(
                        title = currentBookshelf,
                        navigationButton = AppBarItem(Icons.Filled.Menu, "Navigation menu") {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        },
                        menuItems = listOf(AppBarItem(Icons.Filled.Add, "Add") {
                            viewModel.createBookshelf(
                                "Favourite"
                            )
                        }),
                        scrollBehavior = scrollBehavior
                    )
                },
            ) {
                val bookState = viewModel.booksUIState.observeAsState()
                BooksObserver(state = bookState.value!!)
            }
        }
    }

    @Composable
    private fun BookshelfListObserver(
        state: BookshelfListUiState,
        onBookshelfChange: (Bookshelf) -> Unit,
    ) {
        when (state) {
            is BookshelfListUiState.Initializing -> {
                viewModel.getAllBookshelves()
            }

            is BookshelfListUiState.Loading -> {
                CenterCircularProgressBar()
            }

            is BookshelfListUiState.Success -> {
                var index = 1
                NavigationDrawer(
                    items = state.bookshelves.map { bookshelf ->
                        DrawerItem(index++, bookshelf.first, bookshelf.second)
                    },
                    onItemClick = { onBookshelfChange(it.bookshelf) },
                    headerTitle = "Bookshelves"
                )
                onBookshelfChange(state.bookshelves[0].first)
            }

            is BookshelfListUiState.Error -> {}
        }
    }

    @Composable
    private fun BooksObserver(state: BookshelfUIState) {
        when (state) {
            is BookshelfUIState.Initializing -> {
                Text(text = "Initializing", modifier = Modifier.padding(20.dp))
            }

            is BookshelfUIState.Loading -> {
                CenterCircularProgressBar()
            }

            is BookshelfUIState.Success -> {
                Bookshelf(
                    state.books,
                ) { id ->
                    val bundle = bundleOf(DetailsFragment.ID_KEY to id)
                    findNavController().navigate(
                        R.id.action_bookshelfFragment_to_detailsFragment,
                        bundle
                    )
                }
            }

            is BookshelfUIState.Error -> {}
        }
    }
}

