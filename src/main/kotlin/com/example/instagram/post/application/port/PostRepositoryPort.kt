package com.example.instagram.post.application.port

import com.example.instagram.post.domain.Post

interface PostRepositoryPort {
	fun save(post: Post): Post
	fun findById(postId: String): Post?
	fun findAllOrderByCreatedAtDesc(): List<Post>
}
