package com.instagramclone.post.application.port

import com.instagramclone.post.domain.Post

interface PostRepositoryPort {
	fun save(post: Post): Post
	fun findById(postId: String): Post?
	fun findAllOrderByCreatedAtDesc(): List<Post>
}
