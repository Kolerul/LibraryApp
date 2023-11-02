package com.example.libraryapp.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.libraryapp.ui.model.DrawerItem


@Composable
fun NavigationDrawer(
    modifier: Modifier = Modifier,
    headerTitle: String = "Header",
    items: List<DrawerItem>,
    onItemClick: (DrawerItem) -> Unit
) {
    Column(modifier = modifier.background(Color.White)) {
        DrawerHeader(headerTitle)
        DrawerBody(items = items, onItemClick = onItemClick)
    }
}

@Composable
fun DrawerHeader(
    headerTitle: String = "Header"
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(text = headerTitle, fontSize = 30.sp)
    }
}

@Composable
fun DrawerBody(
    items: List<DrawerItem>,
    modifier: Modifier = Modifier,
    onItemClick: (DrawerItem) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(items) { item ->
            Row(
                modifier = Modifier.clickable { onItemClick(item) }
            ) {
                Text(
                    text = item.number.toString(),
                    modifier = Modifier.padding(5.dp)
                )
                Text(
                    text = item.bookshelf.bookshelfTitle,
                    modifier = Modifier
                        .padding(5.dp)
                        .weight(1f)
                )
                Text(
                    text = item.count.toString(),
                    modifier = Modifier
                        .padding(5.dp)
                )
            }
        }
    }
}