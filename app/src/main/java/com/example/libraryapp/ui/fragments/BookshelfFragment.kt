package com.example.libraryapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.Fragment
import com.example.libraryapp.R
import androidx.compose.ui.unit.dp
import com.example.libraryapp.domain.entity.Book
import com.example.libraryapp.ui.compose.TestTheme


class BookshelfFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MaterialTheme {

                }
            }
        }
    }

    @Composable
    fun Bookshelf(books: List<Book>) {
//        Scaffold(
//
//        ) { it ->
//            LazyColumn(contentPadding = it) {
//                items(books){
//                    BookItem(book = it)
//                }
//            }
//        }

    }

    @Composable
    fun BookItem(book: Book) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = book.title
            )
            Row {
                Image(
                    painter = painterResource(id = R.drawable.danvich),
                    contentDescription = "Book preview",
                    modifier = Modifier.height(100.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        book.authors.joinToString(
                            separator = ", "
                        )
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(book.publishedDate)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(book.publisher)
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        book.categories.joinToString(
                            separator = ", "
                        )
                    )
                }
            }
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

}

