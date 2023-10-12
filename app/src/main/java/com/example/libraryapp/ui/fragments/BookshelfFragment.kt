package com.example.libraryapp.ui.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import com.example.libraryapp.R
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.libraryapp.LibraryApp
import com.example.libraryapp.domain.entity.Book
import com.example.libraryapp.presentation.uistate.BookshelfUIState
import com.example.libraryapp.presentation.viewmodel.BookshelfViewModel
import com.example.libraryapp.ui.compose.TestTheme


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
        val state = viewModel.uiState.observeAsState()
        when (state.value) {
            is BookshelfUIState.Initializing -> {
                viewModel.getBooksFromBookshelf("Favourite")
            }

            is BookshelfUIState.Loading -> {
                CircularProgressBar()
            }

            is BookshelfUIState.Success -> {
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

            is BookshelfUIState.Error -> {}
            null -> {}
        }
    }

}

@Composable
fun CircularProgressBar(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .width(100.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        CircularProgressIndicator(
            modifier = modifier.width(100.dp),
            strokeWidth = 10.dp
        )
    }
}

@Composable
fun Bookshelf(
    books: List<Book> = emptyList(),
    onItemClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier.padding(start = 10.dp, end = 10.dp)
    ) {
        items(books) {
            BookItemBackground(
                book = it,
                onItemClick = onItemClick,
                modifier = modifier
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookItemBackground(
    book: Book,
    onItemClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(8.dp)
    ) {
        Surface(
            onClick = {
                onItemClick(book.id)
            }
        ) {
            BookItem(
                book = book,
                modifier = modifier
            )
        }
    }
}

@Composable
fun BookItem(
    book: Book,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        Text(
            text = book.title,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 6.dp)
        )
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.danvich),
                contentDescription = "Book preview",
                modifier = Modifier
                    .height(120.dp)
                    .padding(end = 10.dp)
            )
            Column {
                Text(
                    text = "Авторы: ${
                        book.authors.joinToString(
                            separator = ", "
                        )
                    }",
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Text(
                    text = "Дата публикации: ${book.publishedDate}",
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Text(
                    text = "Издатель: ${book.publisher}",
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Text(
                    text = "Категории: ${
                        book.categories.joinToString(
                            separator = ", "
                        )
                    }",
                    modifier = Modifier.padding(bottom = 10.dp)
                )
            }
        }
    }
}

@Composable
fun ShowMoreIconButton(
    expanded: Boolean,
    onIconClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    IconButton(
        onClick = onIconClick,
        modifier = modifier
    ) {
        Icon(
            imageVector = if (expanded) Icons.Filled.KeyboardArrowUp else Icons.Filled.KeyboardArrowDown,
            contentDescription = if (expanded) stringResource(id = R.string.show_less)
            else stringResource(id = R.string.show_more)
        )
    }
}


@Preview(
    showBackground = true
)
@Composable
fun PreviewShowBook() {
    TestTheme {
        BookItem(
            Book(
                title = "Клинков бесконечный край",
                authors = listOf(
                    "Тосака Рин", "Арчер", "Эмия Широ", "Сейбер"
                ),
                publisher = "Владимир Черных",
                publishedDate = "05.10.2023",
                categories = listOf(
                    "Фентези", "Драмма", "Аниме"
                ),
                description = "Описнаие",
                imageUrl = "dnofnboidfboi",
                id = "1"
            )
        )
    }
}


@Preview(
    showBackground = true
)
@Composable
fun PreviewShowBookshelf() {
    val book = Book(
        title = "Клинков бесконечный край",
        authors = listOf(
            "Тосака Рин", "Арчер", "Эмия Широ", "Сейбер"
        ),
        publisher = "Владимир Черных",
        publishedDate = "05.10.2023",
        categories = listOf(
            "Фентези", "Драмма", "Аниме"
        ),
        description = "Описнаие",
        imageUrl = "dnofnboidfboi",
        id = "1"
    )
    TestTheme {
        Bookshelf(
            books = listOf(
                book,
                book.copy(title = "JOJO"),
                book.copy(title = "The Lord of the rings"),
                book,
                book
            )
        )
    }
}

@Preview(
    showBackground = true
)
@Composable
fun PreviewShowProgressBar() {
    CircularProgressBar()
}

