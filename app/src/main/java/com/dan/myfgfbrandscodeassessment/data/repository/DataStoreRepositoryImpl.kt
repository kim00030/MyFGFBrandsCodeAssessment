package com.dan.myfgfbrandscodeassessment.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.dan.myfgfbrandscodeassessment.domain.repository.DataStoreRepository
import com.dan.myfgfbrandscodeassessment.utils.Constants.LIKE_KEY_PREFIX
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore(name = "like_states")

class DataStoreRepositoryImpl(
    private val context: Context
) : DataStoreRepository {

    override suspend fun saveLikeState(postId: Int, liked: Boolean) {
        val key = booleanPreferencesKey("$LIKE_KEY_PREFIX$postId")
        context.dataStore.edit { preferences ->
            preferences[key] = liked
        }
    }

    override suspend fun getLikeState(postId: Int): Flow<Boolean> {
        val key = booleanPreferencesKey("$LIKE_KEY_PREFIX$postId")
        return context.dataStore.data.map { preferences ->
            preferences[key] ?: false
        }
    }
}
