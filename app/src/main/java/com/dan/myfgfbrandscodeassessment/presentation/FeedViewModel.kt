package com.dan.myfgfbrandscodeassessment.presentation

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dan.myfgfbrandscodeassessment.domain.model.Post
import com.dan.myfgfbrandscodeassessment.domain.repository.PostRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val postRepository: PostRepository
) : ViewModel() {
    var posts = mutableStateListOf<Post>()
        private set

    var isRefreshing = mutableStateOf(false)
        private set

    init {
        loadPosts()
    }

    private fun loadPosts() {
        viewModelScope.launch {
            posts.clear()
            posts.addAll(postRepository.getPosts())
        }
    }

    fun refreshPosts() {
        viewModelScope.launch {

            try {
                isRefreshing.value = true // Set refreshing state to true
                val newPosts = postRepository.refreshPosts() // Simulated API call
                posts.clear()
                posts.addAll(newPosts) // Update the post list
            } finally {
                isRefreshing.value = false // Reset refreshing state
            }
        }

    }

    fun toggleLike(post: Post) {
        val index = posts.indexOf(post)
        if (index != -1) {
            posts[index] = post.copy(liked = !post.liked)
        }
    }
}