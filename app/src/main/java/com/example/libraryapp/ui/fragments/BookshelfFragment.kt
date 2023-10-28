package com.example.libraryapp.ui.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import com.example.libraryapp.R
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.libraryapp.LibraryApp
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.domain.model.Bookshelf
import com.example.libraryapp.presentation.viewmodel.BookshelfViewModel
import com.example.libraryapp.ui.compose.item.AppBarItem
import com.example.libraryapp.ui.compose.Bookshelf
import com.example.libraryapp.ui.compose.DialogWithTextField
import com.example.libraryapp.ui.compose.item.DrawerItem
import com.example.libraryapp.ui.compose.NavigationDrawer
import com.example.libraryapp.ui.compose.SimpleAlertDialog
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
            TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

        val currentBookshelf = viewModel.bookshelf.observeAsState()

        val bookshelves =
            viewModel.getAllBookshelves().collectAsState(initial = emptyList())

        var showAddBookshelfDialog by rememberSaveable {
            mutableStateOf(false)
        }

        var showDeleteAlertDialog by rememberSaveable {
            mutableStateOf(false)
        }

        ModalNavigationDrawer(
            drawerState = drawerState,
            drawerContent = {
                ModalDrawerSheet {
                    BookshelfListObserver(
                        list = bookshelves.value,
                    ) { newBookshelf ->
                        viewModel.getBooksFromBookshelf(newBookshelf)
                        scope.launch {
                            drawerState.apply {
                                if (isClosed) open() else close()
                            }
                        }
                    }
                }
            }
        ) {
            Scaffold(
                modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
                topBar = {
                    TopAppBar(
                        title = currentBookshelf.value!!.bookshelfTitle,
                        navigationButton = AppBarItem(Icons.Filled.Menu, "Navigation menu") {
                            scope.launch {
                                drawerState.apply {
                                    if (isClosed) open() else close()
                                }
                            }
                        },
                        menuItems = listOf(
                            AppBarItem(Icons.Filled.Delete, "Delete") {
                                showDeleteAlertDialog = true
                            },
                            AppBarItem(Icons.Filled.Add, "Add") {
                                showAddBookshelfDialog = true
                            }
                        ),
                        scrollBehavior = scrollBehavior
                    )
                },
            ) {
                val bookState = viewModel.books.observeAsState()
                BooksObserver(list = bookState.value)

                if (showAddBookshelfDialog) {
                    DialogWithTextField(
                        dialogTitle = "Create bookshelf",
                        onDismissRequest = { showAddBookshelfDialog = false },
                        onConfirmClick = { title ->
                            if (title.isNotBlank()) {
                                viewModel.createBookshelf(title)
                                showAddBookshelfDialog = false
                            }
                        }
                    )
                }

                if (showDeleteAlertDialog) {
                    SimpleAlertDialog(
                        title = "Delete bookshelf",
                        text =
                        "Are you sure you want to delete ${currentBookshelf.value!!.bookshelfTitle} bookshelf?",
                        onDismissRequest = { showDeleteAlertDialog = false },
                        onConfirmClick = {
                            viewModel.deleteBookshelf(currentBookshelf.value!!)
                            viewModel.getBooksFromBookshelf(bookshelves.value[0].first)
                            showDeleteAlertDialog = false
                        }
                    )
                }
            }
        }
    }

    @Composable
    private fun BookshelfListObserver(
        list: List<Pair<Bookshelf, Int>>,
        onBookshelfChange: (Bookshelf) -> Unit
    ) {
        var index = 1
        NavigationDrawer(
            items = list.map { bookshelf ->
                DrawerItem(index++, bookshelf.first, bookshelf.second)
            },
            onItemClick = { onBookshelfChange(it.bookshelf) },
            headerTitle = "Bookshelves"
        )
    }


    @Composable
    private fun BooksObserver(list: List<Book>?) {
        if (list.isNullOrEmpty()) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "No books in this bookshelf")
            }
        } else {
            Bookshelf(
                list,
            ) { id ->
                val bundle = bundleOf(DetailsFragment.ID_KEY to id)
                findNavController().navigate(
                    R.id.action_bookshelfFragment_to_detailsFragment,
                    bundle
                )
            }
        }
    }
}

