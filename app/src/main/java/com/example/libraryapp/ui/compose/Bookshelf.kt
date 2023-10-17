package com.example.libraryapp.ui.compose

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.libraryapp.R
import com.example.libraryapp.domain.entity.Book

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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookshelfTopAppBar(
    onAddItemClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Favourite",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Bookshelf menu"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onAddItemClick) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add bookshelf"
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->
        content()
    }
}

@Composable
fun Bookshelf(
    books: List<Book> = emptyList(),
    onItemClick: (String) -> Unit = {},
    contentPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier.padding(start = 10.dp, end = 10.dp),
        contentPadding = contentPadding
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