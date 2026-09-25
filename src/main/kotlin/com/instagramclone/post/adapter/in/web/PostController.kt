package com.instagramclone.post.adapter.`in`.web

import com.instagramclone.post.application.PostUseCase
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/posts")
class PostController(
	private val postUseCase: PostUseCase,
) {
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	fun createPost(@Valid @RequestBody request: CreatePostRequest): PostResponse =
		postUseCase.createPost(request.toCommand()).toResponse()

	@GetMapping
	fun getPosts(): List<PostResponse> =
		postUseCase.getPosts().map { it.toResponse() }

	@GetMapping("/{postId}")
	fun getPost(@PathVariable postId: String): PostResponse =
		postUseCase.getPost(postId).toResponse()

	@PostMapping("/{postId}/likes")
	fun likePost(
		@PathVariable postId: String,
		@Valid @RequestBody request: LikePostRequest,
	): PostResponse =
		postUseCase.likePost(postId, request.toCommand()).toResponse()

	@DeleteMapping("/{postId}/likes/{userName}")
	fun unlikePost(
		@PathVariable postId: String,
		@PathVariable userName: String,
	): PostResponse =
		postUseCase.unlikePost(postId, userName).toResponse()

	@PostMapping("/{postId}/comments")
	fun addComment(
		@PathVariable postId: String,
		@Valid @RequestBody request: AddCommentRequest,
	): PostResponse =
		postUseCase.addComment(postId, request.toCommand()).toResponse()
}
