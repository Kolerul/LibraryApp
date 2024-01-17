package com.example.libraryapp.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.libraryapp.R
import com.example.libraryapp.domain.model.Book

@Composable
fun CompilationTag(
    tagTitle: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Text(
            text = tagTitle,
            color = Color.White,
            modifier = Modifier
                .background(color)
                .padding(top = 5.dp, bottom = 5.dp, start = 10.dp, end = 10.dp),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Composable
fun CompilationBookRow(
    title: String,
    list: List<Book>?,
    modifier: Modifier = Modifier,
    onLoad: (String, Int, Boolean) -> Unit
) {
    var newest by rememberSaveable {
        mutableStateOf(false)
    }

    val color = Color.Black

    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {

            CompilationTag(
                modifier = Modifier.padding(start = 10.dp),
                tagTitle = title,
                color = color
            )

            Spacer(modifier = Modifier.weight(1f))
            Switch(
                checked = newest,
                onCheckedChange = {
                    newest = !newest
                    onLoad(title, 0, newest)
                },
                modifier = Modifier.padding(end = 10.dp),
                colors = SwitchDefaults.colors(
                    checkedTrackColor = color
                )
            )
        }
        if (list.isNullOrEmpty()) {
            onLoad(title, 0, newest)
        } else {
            LazyRow(
                modifier = modifier,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                items(list.size) { index ->
                    CompilationBookItem(book = list[index])
                }
            }
        }
    }
}

@Preview(
    showBackground = true
)
@Composable
fun PreviewCompilationTag() {
    CompilationTag(
        tagTitle = "Detective",
        color = Color.Magenta
    )
}


@Composable
fun CompilationBookItem(
    book: Book,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
    ) {
        Column(
            modifier = modifier.padding(5.dp)
        ) {
            AsyncImage(
                modifier = Modifier
                    .height(120.dp)
                    .width(120.dp)
                    .clip(RoundedCornerShape(10.dp)),
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(book.imageUrl.replace("http", "https"))
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.ic_image_not_found),
                contentDescription = stringResource(id = R.string.book_preview),
                placeholder = painterResource(id = R.drawable.placeholder),
                contentScale = ContentScale.Crop
            )
            Text(
                modifier = Modifier
                    .width(120.dp),
                text = book.title,
                maxLines = 1,
                fontWeight = FontWeight.Bold

            )

            Text(
                modifier = Modifier
                    .width(120.dp),
                text = book.authors.joinToString(
                    separator = ", "
                ),
                maxLines = 1
            )
        }
    }
}


val book = Book(
    title = "Danganronpa",
    authors = listOf("Junko Enoshima", "Kaede Akamatsu"),
    id = "Trigger Happy Havoc",
    publishedDate = "13.01.2024",
    description = "Greatest book of despair of all time",
    publisher = "Vovan incorporated",
    imageUrl = "https://static.wikia.nocookie.net/danganronpa/images/5/50/Junko_Enoshima_%28DR2%29_Halfbody_Sprite_%2815%29.png"
)

@Preview(
    showBackground = true
)
@Composable
fun PreviewCompilationRow() {
    CompilationBookRow(
        title = "Detective",
        list = listOf(book, book, book),
        onLoad = { String, Int, Boolean ->

        }
    )
}

@Preview(
    showBackground = true
)
@Composable
fun PreviewCompilationBook() {
    CompilationBookItem(book = book)
}