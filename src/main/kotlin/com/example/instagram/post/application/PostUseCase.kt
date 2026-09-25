package com.example.instagram.post.application

import com.example.instagram.post.domain.Post

interface PostUseCase {
	fun createPost(command: CreatePostCommand): Post
	fun getPosts(): List<Post>
	fun getPost(postId: String): Post
	fun likePost(postId: String, command: LikePostCommand): Post
	fun unlikePost(postId: String, userName: String): Post
	fun addComment(postId: String, command: AddCommentCommand): Post
}
