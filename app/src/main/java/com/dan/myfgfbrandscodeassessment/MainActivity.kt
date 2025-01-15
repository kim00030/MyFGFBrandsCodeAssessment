package com.dan.myfgfbrandscodeassessment

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dan.myfgfbrandscodeassessment.presentation.FeedScreen
import com.dan.myfgfbrandscodeassessment.presentation.FeedViewModel
import com.dan.myfgfbrandscodeassessment.presentation.event.UiEvent
import com.dan.myfgfbrandscodeassessment.ui.theme.MyFGFBrandsCodeAssessmentTheme
import dagger.hilt.android.AndroidEntryPoint

/*
Create social media feed that:
-show a scrollable feed of post with images
-support like/unlike functionality
-has a comment section
-implemented pull tp refresh
-use pagination got loading more posts

key evaluation:
-List performance
-Memory management
-Network handling
-Complex UI Interaction.

[Social Media Feed Implementation]

-show a scrollable feed of post with images

Used LazyColumn to create a scrollable feed.
Simulated functionality with free images from Pixabay(Free image), reusing two images in the Post model to mimic a real feed.

-support like/unlike functionality

Used icons (ic_thumb_up and ic_thumb_down) that update based on the state of the Post.
State is managed in the FeedViewModel and persists using DataStore.


-has a comment section

Added a comments section where users can view existing comments and dynamically add new ones.
Implemented a TextField for entering comments, and a "Send" icon triggers adding the comment to the post.
Pull-to-Refresh

-Implemented pull-to-refresh functionality using a custom PullToRefreshLazyColumn.
Refreshes the feed and updates the state of posts while retaining the "Like" and "Dislike" states.
Pagination

-use pagination got loading more posts
Currently using LazyColumn for simplicity and performance.
While Paging 3 would be ideal for massive datasets, it was deemed overkill due to the absence of real network APIs.

 */
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyFGFBrandsCodeAssessmentTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    val feedViewModel = hiltViewModel<FeedViewModel>()
                    val posts = feedViewModel.posts
                    val isRefreshing = feedViewModel.isRefreshing.value

                    FeedScreen(
                        posts = posts,
                        onToggleLike = {
                            feedViewModel.onEvent(UiEvent.ToggleLike(it))
                        },
                        modifier = Modifier.padding(innerPadding),
                        isRefreshing = isRefreshing,
                        onRefreshPosts = {
                            feedViewModel.onEvent(UiEvent.RefreshPosts)
                        },
                        onAddComment = { post, comment ->
                            feedViewModel.onEvent(UiEvent.AddComment(post, comment))
                        }
                    )
                }
            }
        }
    }
}
