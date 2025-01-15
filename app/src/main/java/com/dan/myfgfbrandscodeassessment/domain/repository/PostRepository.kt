package com.dan.myfgfbrandscodeassessment.domain.repository

import com.dan.myfgfbrandscodeassessment.domain.model.Post

interface PostRepository {
    suspend fun getPosts(): List<Post>
    suspend fun refreshPosts(): List<Post>
}