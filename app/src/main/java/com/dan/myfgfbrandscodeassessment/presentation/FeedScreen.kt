package com.dan.myfgfbrandscodeassessment.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dan.myfgfbrandscodeassessment.domain.model.Post
import com.dan.myfgfbrandscodeassessment.presentation.components.PullToRefreshLazyColumn

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    posts: List<Post>,
    onToggleLike: (Post) -> Unit,
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    onRefreshPosts: () -> Unit
) {

    val coroutineScope = rememberCoroutineScope()

    Box(modifier = Modifier.fillMaxSize()) {

        PullToRefreshLazyColumn(
            items = posts,
            content = { post ->

                PostItem(
                    post = post,
                    onLikeToggle = {
                        onToggleLike(it)
                    }
                )
            },
            isRefreshing = isRefreshing,
            onRefresh = {
                onRefreshPosts()
            }

        )
    }

}

@Composable
fun PostItem(post: Post, onLikeToggle: (Post) -> Unit) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column {
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)) {
                AsyncImage(
                    model = post.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(ratio = 1280f / 847f)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                TextButton(onClick = { onLikeToggle(post) }) {
                    Text(if (post.liked) "Unlike" else "Like")
                }

            }
            post.comments.forEach {
                Text(
                    text = it,
                    modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
                )
            }
        }
    }
}
