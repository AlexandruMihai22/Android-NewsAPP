package com.example.newsapp.feature_news.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.*
import coil.compose.rememberImagePainter
import com.example.newsapp.feature_news.domain.model.Article


@Composable
fun ArticleListItem(
    article: Article,
    onItemClick: (Article) -> Unit
) {

    Row(
        modifier = Modifier
            .padding(all = 10.dp)
            .clickable { onItemClick(article) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        RemoteImage(
            url = article.urlToImage,
            modifier = Modifier.requiredSize(100.dp)
        )
        WidthSpacer(value = 10.dp)
        Column {
            if (article.source?.name?.isNotEmpty() == true) {
                Text(
                        text = article.source.name,
                    )
                HeightSpacer(value = 4.dp)
            }
            article.title?.let {
                Text(
                    text = it,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
            }
            HeightSpacer(value = 4.dp)
            article.publishedAt?.let {
                Text(
                    text = it.substring(0, 10),
                )
            }
        }
    }
    HeightSpacer(value = 10.dp)
    Divider(
        color = MaterialTheme.colors.secondary.copy(
            alpha = 0.2f
        )
    )
}


@Composable
fun HeightSpacer(value: Dp) {
    Spacer(modifier = Modifier.requiredHeight(value))
}

@Composable
fun WidthSpacer(value: Dp) {
    Spacer(modifier = Modifier.requiredWidth(value))
}


@Composable
fun RemoteImage(
    url: String?,
    modifier: Modifier,
    contentScale: ContentScale = ContentScale.Crop,
    shape: Shape = RoundedCornerShape(5.dp)
) {
    Box(
        modifier = modifier
    ) {
        if (url.isNullOrEmpty()) {
            Icon(
                Icons.Rounded.Menu,
                contentDescription = "Localized description"
            )
        } else {
            Surface(
                color = Color.Transparent,
                shape = shape
            ) {
                Image(
                    painter = rememberImagePainter(url),
                    contentScale = contentScale,
                    contentDescription = "article image",
                    modifier = modifier.fillMaxSize()
                )
            }
        }
    }
}