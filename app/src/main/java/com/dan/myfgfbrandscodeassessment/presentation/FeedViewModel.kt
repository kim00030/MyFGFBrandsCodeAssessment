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
    var posts = mutableStateListOf<Post>()
        private set

    var isRefreshing = mutableStateOf(false)
        private set

    init {
        loadPosts()
    }

    fun onEvent(event: UiEvent) {
        when (event) {
            UiEvent.RefreshPosts -> refreshPosts()
            is UiEvent.ToggleLike -> {
                toggleLike(event.post)
            }
        }
    }

    private fun loadPosts() {
        viewModelScope.launch {
            posts.clear()
            val fetchedPosts = postRepository.getPosts()
            fetchedPosts.forEach { post ->
                val likedState = dataStoreRepository.getLikeState(post.id).first()
                posts.add(post.copy(liked = likedState))
            }
        }
    }

    fun refreshPosts() {
        viewModelScope.launch {
            try {
                isRefreshing.value = true
                val newPosts = postRepository.refreshPosts()
                posts.clear()
                newPosts.forEach { post ->
                    val likedState = dataStoreRepository.getLikeState(post.id).first()
                    posts.add(post.copy(liked = likedState))
                }
            } finally {
                isRefreshing.value = false
            }
        }

    }

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
}