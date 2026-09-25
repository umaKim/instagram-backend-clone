package com.instagramclone.post.application

import com.instagramclone.post.application.port.PostRepositoryPort
import com.instagramclone.post.domain.Comment
import com.instagramclone.post.domain.Post
import com.instagramclone.post.domain.PostNotFoundException
import org.springframework.stereotype.Service

@Service
class PostApplicationService(
	private val postRepository: PostRepositoryPort,
) : PostUseCase {
	override fun createPost(command: CreatePostCommand): Post {
		val post = Post(
			authorName = command.authorName.trim(),
			imageUrl = command.imageUrl.trim(),
			caption = command.caption?.trim()?.takeIf { it.isNotEmpty() },
		)

		return postRepository.save(post)
	}

	override fun getPosts(): List<Post> =
		postRepository.findAllOrderByCreatedAtDesc()

	override fun getPost(postId: String): Post =
		findPost(postId)

	override fun likePost(postId: String, command: LikePostCommand): Post {
		val post = findPost(postId)
		return postRepository.save(post.likeBy(command.userName.trim()))
	}

	override fun unlikePost(postId: String, userName: String): Post {
		val post = findPost(postId)
		return postRepository.save(post.unlikeBy(userName.trim()))
	}

	override fun addComment(postId: String, command: AddCommentCommand): Post {
		val post = findPost(postId)
		val comment = Comment(
			authorName = command.authorName.trim(),
			content = command.content.trim(),
		)

		return postRepository.save(post.addComment(comment))
	}

	private fun findPost(postId: String): Post =
		postRepository.findById(postId)
			?: throw PostNotFoundException(postId)
}
