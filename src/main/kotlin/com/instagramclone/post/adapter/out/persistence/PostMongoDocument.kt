package com.instagramclone.post.adapter.out.persistence

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.time.Instant

@Document("posts")
data class PostMongoDocument(
	@Id
	val id: String? = null,
	@Indexed
	val authorName: String,
	val imageUrl: String,
	val caption: String? = null,
	val likedBy: Set<String> = emptySet(),
	val comments: List<CommentMongoDocument> = emptyList(),
	@Indexed
	val createdAt: Instant = Instant.now(),
	val updatedAt: Instant = Instant.now(),
)

data class CommentMongoDocument(
	val id: String,
	val authorName: String,
	val content: String,
	val createdAt: Instant,
)
