package com.dan.myfgfbrandscodeassessment.domain.model

// Data Model
data class Post(
    val id: Int,
    val imageUrl: String,
    var liked: Boolean = false,
    var comments: List<String> = listOf("Great post!", "Nice!")
)