package com.dan.myfgfbrandscodeassessment.data.repository

import com.dan.myfgfbrandscodeassessment.domain.model.Post
import com.dan.myfgfbrandscodeassessment.domain.repository.PostRepository
import com.dan.myfgfbrandscodeassessment.utils.Util
import kotlinx.coroutines.delay

class PostRepositoryImpl : PostRepository {

    private val postsCache = mutableListOf<Post>()

    override suspend fun getPosts(): List<Post> {
        if (postsCache.isEmpty()) {
            // Simulating API call
            delay(100)
            postsCache.addAll((1..100).map {
                Post(
                    id = it,
                    imageUrl = "https://cdn.pixabay.com/photo/2016/09/07/10/37/kermit-1651325_1280.jpg",
                    liked = false,
                    comments = Util.randomizedComments()
                )
            })
        }
        return postsCache
    }

    override suspend fun refreshPosts(): List<Post> {
        delay(100) // Simulate network delay
        postsCache.clear()
        postsCache.addAll((1..100).map {
            Post(
                id = it,
                imageUrl = "https://cdn.pixabay.com/photo/2017/01/29/14/19/kermit-2018085_1280.jpg",
                liked = false,
                comments = Util.randomizedComments()
            )
        })
        return postsCache
    }
}