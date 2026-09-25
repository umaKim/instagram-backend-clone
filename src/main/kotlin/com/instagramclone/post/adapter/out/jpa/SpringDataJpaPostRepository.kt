package com.instagramclone.post.adapter.out.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface SpringDataJpaPostRepository : JpaRepository<PostJpaEntity, String> {
	fun findAllByOrderByCreatedAtDesc(): List<PostJpaEntity>
}
