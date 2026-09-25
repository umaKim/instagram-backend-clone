package com.instagramclone.post.adapter.out.persistence

import com.instagramclone.post.domain.Comment
import com.instagramclone.post.domain.Post

fun Post.toDocument(): PostMongoDocument =
	PostMongoDocument(
		id = id,
		authorName = authorName,
		imageUrl = imageUrl,
		caption = caption,
		likedBy = likedBy,
		comments = comments.map { it.toDocument() },
		createdAt = createdAt,
		updatedAt = updatedAt,
	)

fun PostMongoDocument.toDomain(): Post =
	Post(
		id = id,
		authorName = authorName,
		imageUrl = imageUrl,
		caption = caption,
		likedBy = likedBy,
		comments = comments.map { it.toDomain() },
		createdAt = createdAt,
		updatedAt = updatedAt,
	)

private fun Comment.toDocument(): CommentMongoDocument =
	CommentMongoDocument(
		id = id,
		authorName = authorName,
		content = content,
		createdAt = createdAt,
	)

private fun CommentMongoDocument.toDomain(): Comment =
	Comment(
		id = id,
		authorName = authorName,
		content = content,
		createdAt = createdAt,
	)
