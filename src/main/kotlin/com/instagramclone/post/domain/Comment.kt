package com.instagramclone.post.domain

import java.time.Instant
import java.util.UUID

data class Comment(
	val id: String = UUID.randomUUID().toString(),
	val authorName: String,
	val content: String,
	val createdAt: Instant = Instant.now(),
)
