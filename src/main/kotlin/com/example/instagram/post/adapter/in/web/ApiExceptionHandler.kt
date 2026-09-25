package com.example.instagram.post.adapter.`in`.web

import com.example.instagram.post.domain.PostNotFoundException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

@RestControllerAdvice
class ApiExceptionHandler {
	@ExceptionHandler(PostNotFoundException::class)
	fun handlePostNotFound(exception: PostNotFoundException): ResponseEntity<ApiErrorResponse> =
		ResponseEntity.status(HttpStatus.NOT_FOUND)
			.body(ApiErrorResponse(message = exception.message ?: "Post not found"))

	@ExceptionHandler(MethodArgumentNotValidException::class)
	fun handleValidation(exception: MethodArgumentNotValidException): ResponseEntity<ApiErrorResponse> {
		val errors = exception.bindingResult.fieldErrors.associate { fieldError ->
			fieldError.field to (fieldError.defaultMessage ?: "Invalid value")
		}

		return ResponseEntity.badRequest()
			.body(ApiErrorResponse(message = "Validation failed", errors = errors))
	}
}

data class ApiErrorResponse(
	val message: String,
	val errors: Map<String, String> = emptyMap(),
)
