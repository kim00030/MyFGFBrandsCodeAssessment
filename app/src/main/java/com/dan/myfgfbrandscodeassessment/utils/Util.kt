package com.dan.myfgfbrandscodeassessment.utils

object Util {

    fun randomizedComments(): List<String> {
        val allComments = listOf(
            "Great post!",
            "Amazing!",
            "Looks good!",
            "I love it!",
            "Interesting view!",
            "Well done!",
            "Nice shot!",
            "Fantastic!"
        )
        val randomCount = allComments.indices.random() // Random number of unique comments
        return allComments.shuffled().take(randomCount) // Shuffle and take unique comments
    }
}