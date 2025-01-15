package com.dan.myfgfbrandscodeassessment.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.dan.myfgfbrandscodeassessment.domain.model.Post
import com.dan.myfgfbrandscodeassessment.presentation.components.PostItem
import com.dan.myfgfbrandscodeassessment.presentation.components.PullToRefreshLazyColumn

/**
 * Displays a scrollable feed of posts with support for pull-to-refresh,
 * liking/unliking posts, and adding comments.
 *
 * @param posts The list of posts to display.
 * @param onToggleLike Callback to toggle the like/dislike state of a post.
 * @param modifier Modifier to customize the layout.
 * @param isRefreshing Indicates whether a pull-to-refresh operation is in progress.
 * @param onRefreshPosts Callback to refresh the feed when a pull-to-refresh is triggered.
 * @param onAddComment Callback to add a comment to a specific post.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FeedScreen(
    posts: List<Post>,
    onToggleLike: (Post) -> Unit,
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    onRefreshPosts: () -> Unit,
    onAddComment: (Post, String) -> Unit,
) {

    Box(modifier = modifier.fillMaxSize()) {

        PullToRefreshLazyColumn(
            items = posts,
            content = { post ->

                PostItem(
                    post = post,
                    onLikeToggle = {
                        onToggleLike(it)
                    },
                    onAddComment = { comment ->
                        onAddComment(post, comment)
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

