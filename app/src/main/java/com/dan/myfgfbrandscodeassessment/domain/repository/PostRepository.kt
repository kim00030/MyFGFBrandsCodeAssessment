package com.dan.myfgfbrandscodeassessment.domain.repository

import com.dan.myfgfbrandscodeassessment.domain.model.Post

/**
 * Defines the contract for accessing and managing post data.
 *
 * This interface provides methods for fetching posts and refreshing the feed,
 * simulating the behavior of a typical data source (e.g., a network API or database).
 */
interface PostRepository {
    /**
     * Fetches the initial list of posts.
     *
     * This method is used to retrieve the posts when the feed is first loaded.
     *
     * @return A list of posts to display in the feed.
     */
    suspend fun getPosts(): List<Post>

    /**
     * Refreshes the list of posts.
     *
     * This method is used to simulate the behavior of refreshing the feed,
     * typically triggered during pull-to-refresh operations.
     *
     * @return A new list of posts to update the feed.
     */
    suspend fun refreshPosts(): List<Post>
}