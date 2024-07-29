package com.prajwal.stalenews.presentation.ui

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prajwal.stalenews.R
import com.prajwal.stalenews.data.models.ArticlesItem


@Composable
fun NewsSourcesListItem(data: ArticlesItem, modifier: Modifier = Modifier, isBookmarked: Boolean) {
    Column(modifier.fillMaxWidth()) {
        NewsCard(data = data, null, null, isBookmarked)
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterChips(
    categories: List<String>,
    selectedCategory: String?,
    onCategorySelected: (String?) -> Unit
) {
    // Row of filter chips
    LazyRow(modifier = Modifier.padding(8.dp)) {
        items(categories) { category ->
            FilterChip(
                modifier = Modifier.padding(3.dp),
                selected = selectedCategory == category,
                onClick = { onCategorySelected(if (selectedCategory == category) null else category) },
                label = { Text(category) }
            )
        }
    }
}
@Composable
fun NewsList(
    newsSource: List<ArticlesItem?>,
    onItemClick: (ArticlesItem) -> Unit
) {
    var bookmarkedItems by remember { mutableStateOf(setOf<ArticlesItem>()) }
    LazyColumn {
        items(newsSource) { newsSource ->
            val isBookmarked = bookmarkedItems.contains(newsSource)
            if (newsSource != null) {
                NewsCard(
                    newsSource,
                    onBookmarkClickAction = {
                        // Toggle bookmark state
                        bookmarkedItems = if (isBookmarked) {
                            bookmarkedItems - newsSource
                        } else {
                            bookmarkedItems + newsSource
                        }
                    },
                    onClickAction = { onItemClick(newsSource) },
                    isBookmarked = isBookmarked
                )
            }
        }
    }
}
@Composable
fun NewsCard(
    data: ArticlesItem,
    onClickAction: (() -> Unit)?,
    onBookmarkClickAction: (() -> Unit)?,
    isBookmarked: Boolean
) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                if (onClickAction != null) {
                    onClickAction()
                }
            },
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Spacer(modifier = Modifier.width(8.dp))
            Column(modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth(0.8f)) {
                (data.author ?: data.source?.name)?.let {
                    Text(
                        text = it,
                        fontSize = 20.sp,
                        color = Color.Black
                    )
                }
                data.title?.let { Text(text = it, fontSize = 14.sp, color = Color.Gray) }
                data.description?.let { Text(text = it, fontSize = 14.sp, color = Color.Gray) }
            }
            Spacer(modifier = Modifier.width(8.dp))
            Column {
                IconButton(
                    onClick = {
                        if (onBookmarkClickAction != null) {
                            onBookmarkClickAction()
                        }
                    },
                    modifier = Modifier
                        .padding(16.dp)
                        .width(100.dp)
                ) {
                    Icon(
                        imageVector = if (isBookmarked) ImageVector.vectorResource(id = R.drawable.bookmark_added) else ImageVector.vectorResource(
                            id = R.drawable.bookmark_icon
                        ),
                        contentDescription = if (isBookmarked) "Bookmarked" else "Bookmark"
                    )
                }
            }

        }
    }
}

@Preview
@Preview(uiMode = Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun NewsListItemPreview() {
    val data = ArticlesItem(
        author = "ABC News",
        title = "Deadpool & Wolverine’ Scores Mightier-Than-Expected \$211 Million, " +
                "Sixth-Biggest Debut in Box Office History - Variety",
        description = "Your trusted source for breaking news, analysis, exclusive interviews, " +
                "headlines, and videos at ABCNews.com.",
        url = "https://abcnews.go.com",
    )
    NewsSourcesListItem( data, Modifier.padding(5.dp), false )
}
@Preview
@Preview(uiMode = Configuration.UI_MODE_TYPE_NORMAL)
@Composable
fun FilterChipsItemPreview() {
    FilterChips(categories = listOf(
        "business",
        "entertainment",
        "general",
        "health",
        "science",
        "sports",
        "technology"
    ), selectedCategory = "business") {
    }
}