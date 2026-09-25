package com.instagramclone.post.domain

import java.time.Instant

data class Post(
	val id: String? = null,
	val authorName: String,
	val imageUrl: String,
	val caption: String? = null,
	val likedBy: Set<String> = emptySet(),
	val comments: List<Comment> = emptyList(),
	val createdAt: Instant = Instant.now(),
	val updatedAt: Instant = Instant.now(),
) {
	fun likeBy(userName: String, now: Instant = Instant.now()): Post =
		copy(likedBy = likedBy + userName, updatedAt = now)

	fun unlikeBy(userName: String, now: Instant = Instant.now()): Post =
		copy(likedBy = likedBy - userName, updatedAt = now)

	fun addComment(comment: Comment, now: Instant = Instant.now()): Post =
		copy(comments = comments + comment, updatedAt = now)
}
