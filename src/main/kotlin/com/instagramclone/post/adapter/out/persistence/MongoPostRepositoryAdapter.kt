package com.instagramclone.post.adapter.out.persistence

import com.instagramclone.post.application.port.PostRepositoryPort
import com.instagramclone.post.domain.Post
import org.springframework.context.annotation.Profile
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Repository

@Repository
@Profile("dev")
class MongoPostRepositoryAdapter(
	private val mongoTemplate: MongoTemplate,
) : PostRepositoryPort {
	override fun save(post: Post): Post =
		mongoTemplate.save(post.toDocument()).toDomain()

	override fun findById(postId: String): Post? =
		mongoTemplate.findById(postId, PostMongoDocument::class.java)?.toDomain()

	override fun findAllOrderByCreatedAtDesc(): List<Post> =
		mongoTemplate.find(
			Query().with(Sort.by(Sort.Direction.DESC, "createdAt")),
			PostMongoDocument::class.java,
		).map { it.toDomain() }
}