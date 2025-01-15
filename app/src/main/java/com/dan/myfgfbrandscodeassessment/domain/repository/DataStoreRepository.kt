package com.dan.myfgfbrandscodeassessment.domain.repository

import kotlinx.coroutines.flow.Flow
/**
 * Provides an abstraction for managing persistent data using DataStore.
 *
 * This repository handles the storage and retrieval of like/dislike states and comments
 * for posts, ensuring data is persistently saved and accessible across app sessions.
 */
interface DataStoreRepository {
    // Like/Dislike state
    suspend fun saveLikeState(postId: Int, liked: Boolean)
    suspend fun getLikeState(postId: Int): Flow<Boolean>

    // Comment state
    suspend fun saveComments(postId: Int, comments: List<String>)
    fun getComments(postId: Int): Flow<List<String>>
}