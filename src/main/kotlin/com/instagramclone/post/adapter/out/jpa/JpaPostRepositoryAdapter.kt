package com.instagramclone.post.adapter.out.jpa

import com.instagramclone.post.application.port.PostRepositoryPort
import com.instagramclone.post.domain.Post
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Repository

@Repository
@Profile("local")
class JpaPostRepositoryAdapter(
	private val repository: SpringDataJpaPostRepository,
) : PostRepositoryPort {
	override fun save(post: Post): Post =
		repository.save(post.toJpaEntity()).toDomain()

	override fun findById(postId: String): Post? =
		repository.findById(postId).map { it.toDomain() }.orElse(null)

	override fun findAllOrderByCreatedAtDesc(): List<Post> =
		repository.findAllByOrderByCreatedAtDesc().map { it.toDomain() }
}