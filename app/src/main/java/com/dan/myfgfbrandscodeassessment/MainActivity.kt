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
                        }
                    )
                }
            }
        }
    }
}
