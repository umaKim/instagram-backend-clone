package com.instagramclone.post.domain

class PostNotFoundException(postId: String) : RuntimeException("Post not found: $postId")
