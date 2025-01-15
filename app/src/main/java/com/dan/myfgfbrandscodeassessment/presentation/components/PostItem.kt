package com.dan.myfgfbrandscodeassessment.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.dan.myfgfbrandscodeassessment.R
import com.dan.myfgfbrandscodeassessment.domain.model.Post

/**
 * A composable that displays a single post in the feed, including its image, like/dislike button,
 * comments section, and input for adding new comments.
 *
 * @param post The data for the post to be displayed, including its image, like state, and comments.
 * @param onLikeToggle Callback to toggle the like/dislike state of the post.
 * @param onAddComment Callback to add a new comment to the post.
 */
@Composable
fun PostItem(
    post: Post,
    onLikeToggle: (Post) -> Unit,
    onAddComment: (String) -> Unit,
) {
    Card(modifier = Modifier.fillMaxWidth()) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                AsyncImage(
                    model = post.imageUrl,
                    contentDescription = null,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(ratio = 1280f / 847f)
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                IconButton(onClick = { onLikeToggle(post) }) {
                    Icon(
                        painter = painterResource(
                            id = if (post.liked) R.drawable.ic_thumb_down else R.drawable.ic_thumb_up
                        ),
                        contentDescription = if (post.liked) "Dislike" else "Like",
                        tint = if (post.liked) Color.Red else Color.Green
                    )
                }

            }

            Text(
                text = "Comments:",
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 2.dp)
            )

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp),
                thickness = 1.dp,
                color = Color.Gray
            )

            post.comments.forEach {
                Text(
                    text = it,
                    modifier = Modifier.padding(start = 16.dp, bottom = 4.dp)
                )
            }

            // Add Comment Input
            var commentText by remember { mutableStateOf("") }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TextField(
                    value = commentText,
                    onValueChange = { commentText = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Add a comment...") }
                )
                IconButton(
                    onClick = {
                        if (commentText.isNotBlank()) {
                            onAddComment(commentText)
                            commentText = ""
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_send),
                        contentDescription = "Send Comment"
                    )
                }
            }
        }
    }
}
