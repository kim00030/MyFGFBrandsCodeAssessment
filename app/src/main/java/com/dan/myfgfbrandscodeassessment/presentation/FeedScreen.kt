package com.dan.myfgfbrandscodeassessment.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dan.myfgfbrandscodeassessment.domain.model.Post
import com.dan.myfgfbrandscodeassessment.presentation.components.PostItem
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

    Box(modifier = modifier.fillMaxSize()) {

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

