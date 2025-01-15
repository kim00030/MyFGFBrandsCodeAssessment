package com.dan.myfgfbrandscodeassessment.presentation.event

import com.dan.myfgfbrandscodeassessment.domain.model.Post


sealed class UiEvent {
    data object RefreshPosts : UiEvent()
    data class ToggleLike(val post: Post) : UiEvent()
}