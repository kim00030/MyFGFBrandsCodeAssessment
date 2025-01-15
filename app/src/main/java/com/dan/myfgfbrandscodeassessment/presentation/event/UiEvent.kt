package com.dan.myfgfbrandscodeassessment.presentation.event

import com.dan.myfgfbrandscodeassessment.domain.model.Post

/**
 * Represents UI events that can be triggered in the feed screen.
 *
 * This sealed class defines different types of actions that can occur,
 * such as refreshing posts, toggling the like state, or adding a comment.
 */
sealed class UiEvent {
    data object RefreshPosts : UiEvent()
    data class ToggleLike(val post: Post) : UiEvent()
    data class AddComment(val post: Post, val comment: String) : UiEvent()
}