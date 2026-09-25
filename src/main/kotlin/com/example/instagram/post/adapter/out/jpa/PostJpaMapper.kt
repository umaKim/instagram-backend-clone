package com.example.instagram.post.adapter.out.jpa

import com.example.instagram.post.domain.Comment
import com.example.instagram.post.domain.Post

fun Post.toJpaEntity(): PostJpaEntity {
	val entity = PostJpaEntity(
		id = id ?: java.util.UUID.randomUUID().toString(),
		authorName = authorName,
		imageUrl = imageUrl,
		caption = caption,
		likedBy = likedBy.toMutableSet(),
		createdAt = createdAt,
		updatedAt = updatedAt,
	)
	entity.comments = comments.map { it.toJpaEntity(entity) }.toMutableList()
	return entity
}

fun PostJpaEntity.toDomain(): Post =
	Post(
		id = id,
		authorName = authorName,
		imageUrl = imageUrl,
		caption = caption,
		likedBy = likedBy.toSet(),
		comments = comments.map { it.toDomain() },
		createdAt = createdAt,
		updatedAt = updatedAt,
	)

private fun Comment.toJpaEntity(post: PostJpaEntity): CommentJpaEntity =
	CommentJpaEntity(
		id = id,
		authorName = authorName,
		content = content,
		createdAt = createdAt,
		post = post,
	)

private fun CommentJpaEntity.toDomain(): Comment =
	Comment(
		id = id,
		authorName = authorName,
		content = content,
		createdAt = createdAt,
	)
