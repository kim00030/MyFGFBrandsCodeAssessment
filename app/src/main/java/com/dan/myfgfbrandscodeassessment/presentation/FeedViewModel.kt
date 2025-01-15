package com.dan.myfgfbrandscodeassessment.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dan.myfgfbrandscodeassessment.domain.model.Post
import com.dan.myfgfbrandscodeassessment.domain.repository.DataStoreRepository
import com.dan.myfgfbrandscodeassessment.domain.repository.PostRepository
import com.dan.myfgfbrandscodeassessment.presentation.event.UiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val postRepository: PostRepository,
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {
    // Mutable state list to hold the posts for the feed
    var posts = mutableStateListOf<Post>()
        private set

    // State to track the pull-to-refresh functionality
    var isRefreshing = mutableStateOf(false)
        private set

    // Initialization block to load posts when the ViewModel is created
    init {
        loadPosts()
    }

    /**
     * Handles different types of UI events and triggers the corresponding functions.
     * @param event The UI event to handle (e.g., RefreshPosts, ToggleLike, AddComment).
     */
    fun onEvent(event: UiEvent) {
        when (event) {
            UiEvent.RefreshPosts -> refreshPosts()
            is UiEvent.ToggleLike -> {
                toggleLike(event.post)
            }

            is UiEvent.AddComment -> addComment(event.post, event.comment)
        }
    }

    /**
     * Loads the initial list of posts from the repository.
     * Fetches and updates the posts with their current like states and comments from DataStore.
     */
    private fun loadPosts() {
        viewModelScope.launch {
            posts.clear()

            // Fetch posts from the repository
            val fetchedPosts = postRepository.getPosts()
            val updatedPosts = fetchedPosts.map { post ->
                // Retrieve the like state and comments for each post from DataStore
                val comments = dataStoreRepository.getComments(post.id).first()
                val likedState = dataStoreRepository.getLikeState(post.id).first()
                post.copy(comments = comments, liked = likedState)
            }
            // Update the state with the fetched posts
            posts.addAll(updatedPosts)
        }
    }

    /**
     * Refreshes the list of posts during pull-to-refresh.
     * Fetches new posts and updates their like states from DataStore.
     */
    private fun refreshPosts() {
        viewModelScope.launch {
            try {
                isRefreshing.value = true
                val newPosts = postRepository.refreshPosts()
                posts.clear()
                newPosts.forEach { post ->
                    // Retrieve the like state for each post from DataStore
                    val likedState = dataStoreRepository.getLikeState(post.id).first()
                    posts.add(post.copy(liked = likedState))
                }
            } finally {
                isRefreshing.value = false
            }
        }

    }

    /**
     * Toggles the like state of a specific post.
     * Updates the state in memory and persists it in DataStore.
     * @param post The post whose like state is to be toggled.
     */
    private fun toggleLike(post: Post) {
        val index = posts.indexOf(post)
        if (index != -1) {
            val newLikedState = !post.liked
            posts[index] = post.copy(liked = newLikedState)

            // Save the new state persistently
            viewModelScope.launch {
                dataStoreRepository.saveLikeState(post.id, newLikedState)
            }
        }
    }

    /**
     * Adds a new comment to a specific post.
     * Updates the state in memory and persists the comments in DataStore.
     * @param post The post to which the comment is added.
     * @param comment The new comment to add.
     */
    private fun addComment(post: Post, comment: String) {
        val index = posts.indexOf(post)
        if (index != -1) {
            // Add the new comment
            val updatedComments = post.comments + comment
            // Update the post in memory
            posts[index] = post.copy(comments = updatedComments)

            // Persist the updated comments
            viewModelScope.launch {
                dataStoreRepository.saveComments(post.id, updatedComments)
            }
        }
    }
}