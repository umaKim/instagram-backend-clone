package com.example.instagram.post.domain

class PostNotFoundException(postId: String) : RuntimeException("Post not found: $postId")
