package com.dan.myfgfbrandscodeassessment.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.dan.myfgfbrandscodeassessment.domain.repository.DataStoreRepository
import com.dan.myfgfbrandscodeassessment.utils.Constants.LIKE_KEY_PREFIX
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/**
 * An implementation of DataStoreRepository for managing persistent data.
 *
 * This class provides methods to save and retrieve the like/dislike state
 * and comments for posts using Android's DataStore API.
 */
private val Context.dataStore by preferencesDataStore(name = "like_states")

class DataStoreRepositoryImpl(
    private val context: Context
) : DataStoreRepository {
    // Save Like/Dislike State
    override suspend fun saveLikeState(postId: Int, liked: Boolean) {
        val key = booleanPreferencesKey("$LIKE_KEY_PREFIX$postId")
        context.dataStore.edit { preferences ->
            preferences[key] = liked
        }
    }

    // Get Like/Dislike State
    override suspend fun getLikeState(postId: Int): Flow<Boolean> {
        val key = booleanPreferencesKey("$LIKE_KEY_PREFIX$postId")
        return context.dataStore.data.map { preferences ->
            preferences[key] ?: false
        }
    }

    // Save Comments
    override suspend fun saveComments(postId: Int, comments: List<String>) {
        val key = stringSetPreferencesKey("comments_post_$postId")
        context.dataStore.edit { preferences ->
            preferences[key] = comments.toSet()
        }
    }

    // Get Comments
    override fun getComments(postId: Int): Flow<List<String>> {
        val key = stringSetPreferencesKey("comments_post_$postId")
        return context.dataStore.data.map { preferences ->
            preferences[key]?.toList() ?: emptyList()
        }
    }
}
