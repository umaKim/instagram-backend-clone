package com.instagramclone.post.application

data class CreatePostCommand(
	val authorName: String,
	val imageUrl: String,
	val caption: String?,
)

data class LikePostCommand(
	val userName: String,
)

data class AddCommentCommand(
	val authorName: String,
	val content: String,
)
