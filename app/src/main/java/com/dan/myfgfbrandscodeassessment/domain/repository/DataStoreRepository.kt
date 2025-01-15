package com.dan.myfgfbrandscodeassessment.domain.repository

import kotlinx.coroutines.flow.Flow

interface DataStoreRepository {
    suspend fun saveLikeState(postId: Int, liked: Boolean)
    suspend fun getLikeState(postId: Int): Flow<Boolean>
}