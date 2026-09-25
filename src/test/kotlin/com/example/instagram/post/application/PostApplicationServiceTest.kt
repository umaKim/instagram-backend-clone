package com.example.instagram.post.application

import com.example.instagram.post.application.port.PostRepositoryPort
import com.example.instagram.post.domain.Post
import com.example.instagram.post.domain.PostNotFoundException
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class PostApplicationServiceTest {
	private lateinit var repository: InMemoryPostRepository
	private lateinit var service: PostApplicationService

	@BeforeEach
	fun setUp() {
		repository = InMemoryPostRepository()
		service = PostApplicationService(repository)
	}

	@Test
	fun `creates post with author name and image url`() {
		val response = service.createPost(
			CreatePostCommand(
				authorName = " uma ",
				imageUrl = " https://example.com/photo.jpg ",
				caption = " hello ",
			),
		)

		assertThat(response.id).isEqualTo("post-1")
		assertThat(response.authorName).isEqualTo("uma")
		assertThat(response.imageUrl).isEqualTo("https://example.com/photo.jpg")
		assertThat(response.caption).isEqualTo("hello")
		assertThat(response.likedBy).isEmpty()
		assertThat(response.comments).isEmpty()
	}

	@Test
	fun `likes post only once per user`() {
		repository.save(
			Post(
				id = "post-1",
				authorName = "uma",
				imageUrl = "https://example.com/photo.jpg",
				likedBy = setOf("minji"),
			),
		)

		val response = service.likePost("post-1", LikePostCommand("minji"))

		assertThat(response.likedBy).containsExactly("minji")
	}

	@Test
	fun `adds comment to post`() {
		repository.save(
			Post(
				id = "post-1",
				authorName = "uma",
				imageUrl = "https://example.com/photo.jpg",
			),
		)

		val response = service.addComment(
			"post-1",
			AddCommentCommand(authorName = " june ", content = " nice photo! "),
		)

		assertThat(response.comments).hasSize(1)
		assertThat(response.comments.single().authorName).isEqualTo("june")
		assertThat(response.comments.single().content).isEqualTo("nice photo!")
	}

	@Test
	fun `throws when post does not exist`() {
		assertThatThrownBy { service.getPost("missing") }
			.isInstanceOf(PostNotFoundException::class.java)
			.hasMessageContaining("missing")
	}
}

private class InMemoryPostRepository : PostRepositoryPort {
	private val posts = linkedMapOf<String, Post>()
	private var nextId = 1

	override fun save(post: Post): Post {
		val savedPost = post.copy(id = post.id ?: "post-${nextId++}")
		posts[requireNotNull(savedPost.id)] = savedPost
		return savedPost
	}

	override fun findById(postId: String): Post? =
		posts[postId]

	override fun findAllOrderByCreatedAtDesc(): List<Post> =
		posts.values.sortedByDescending { it.createdAt }
}
