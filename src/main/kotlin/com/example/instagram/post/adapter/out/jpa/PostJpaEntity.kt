package com.example.instagram.post.adapter.out.jpa

import jakarta.persistence.CascadeType
import jakarta.persistence.Column
import jakarta.persistence.ElementCollection
import jakarta.persistence.CollectionTable
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.OneToMany
import jakarta.persistence.OrderBy
import jakarta.persistence.Table
import java.time.Instant
import java.util.UUID

@Entity
@Table(name = "posts")
class PostJpaEntity(
	@Id
	@Column(name = "id", nullable = false)
	var id: String = UUID.randomUUID().toString(),

	@Column(name = "author_name", nullable = false)
	var authorName: String = "",

	@Column(name = "image_url", nullable = false, length = 2048)
	var imageUrl: String = "",

	@Column(name = "caption")
	var caption: String? = null,

	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(
		name = "post_likes",
		joinColumns = [JoinColumn(name = "post_id")],
	)
	@Column(name = "user_name", nullable = false)
	var likedBy: MutableSet<String> = linkedSetOf(),

	@OneToMany(
		mappedBy = "post",
		cascade = [CascadeType.ALL],
		orphanRemoval = true,
		fetch = FetchType.EAGER,
	)
	@OrderBy("createdAt ASC")
	var comments: MutableList<CommentJpaEntity> = mutableListOf(),

	@Column(name = "created_at", nullable = false)
	var createdAt: Instant = Instant.now(),

	@Column(name = "updated_at", nullable = false)
	var updatedAt: Instant = Instant.now(),
)

@Entity
@Table(name = "comments")
class CommentJpaEntity(
	@Id
	@Column(name = "id", nullable = false)
	var id: String = UUID.randomUUID().toString(),

	@Column(name = "author_name", nullable = false)
	var authorName: String = "",

	@Column(name = "content", nullable = false, length = 2000)
	var content: String = "",

	@Column(name = "created_at", nullable = false)
	var createdAt: Instant = Instant.now(),

	@jakarta.persistence.ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "post_id", nullable = false)
	var post: PostJpaEntity? = null,
)
