package com.example.instagram.post.adapter.`in`.web

import com.example.instagram.post.domain.Comment
import com.example.instagram.post.domain.Post
import java.time.Instant

data class PostResponse(
	val id: String,
	val authorName: String,
	val caption: String?,
	val imageUrl: String,
	val likeCount: Int,
	val likedBy: Set<String>,
	val comments: List<CommentResponse>,
	val commentCount: Int,
	val createdAt: Instant,
	val updatedAt: Instant,
)

data class CommentResponse(
	val id: String,
	val authorName: String,
	val content: String,
	val createdAt: Instant,
)

fun Post.toResponse(): PostResponse =
	PostResponse(
		id = requireNotNull(id),
		authorName = authorName,
		caption = caption,
		imageUrl = imageUrl,
		likeCount = likedBy.size,
		likedBy = likedBy,
		comments = comments.map { it.toResponse() },
		commentCount = comments.size,
		createdAt = createdAt,
		updatedAt = updatedAt,
	)

private fun Comment.toResponse(): CommentResponse =
	CommentResponse(
		id = id,
		authorName = authorName,
		content = content,
		createdAt = createdAt,
	)
