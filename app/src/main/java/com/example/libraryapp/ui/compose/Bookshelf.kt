package com.example.libraryapp.ui.compose


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.libraryapp.R
import com.example.libraryapp.domain.model.Book
import com.example.libraryapp.ui.compose.settings.TestTheme

@Composable
fun Bookshelf(
    modifier: Modifier = Modifier,
    books: List<Book> = emptyList(),
    contentPadding: PaddingValues = PaddingValues(0.dp),
    onItemClick: (String) -> Unit = {}
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = modifier.padding(contentPadding)
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
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp),
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
        modifier = modifier
            .fillMaxWidth()
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
            AsyncImage(
                modifier = Modifier
                    .height(120.dp)
                    .padding(end = 10.dp),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(book.imageUrl.replace("http", "https"))
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.ic_image_not_found),
                contentDescription = stringResource(id = R.string.book_preview),
                placeholder = painterResource(id = R.drawable.placeholder)
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
    CenterCircularProgressBar()
}