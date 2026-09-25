package com.instagramclone.post.adapter.`in`.web

import com.instagramclone.post.application.AddCommentCommand
import com.instagramclone.post.application.CreatePostCommand
import com.instagramclone.post.application.LikePostCommand
import jakarta.validation.constraints.NotBlank

data class CreatePostRequest(
	@field:NotBlank
	val authorName: String,
	@field:NotBlank
	val imageUrl: String,
	val caption: String? = null,
) {
	fun toCommand(): CreatePostCommand =
		CreatePostCommand(
			authorName = authorName,
			imageUrl = imageUrl,
			caption = caption,
		)
}

data class LikePostRequest(
	@field:NotBlank
	val userName: String,
) {
	fun toCommand(): LikePostCommand =
		LikePostCommand(userName = userName)
}

data class AddCommentRequest(
	@field:NotBlank
	val authorName: String,
	@field:NotBlank
	val content: String,
) {
	fun toCommand(): AddCommentCommand =
		AddCommentCommand(
			authorName = authorName,
			content = content,
		)
}
