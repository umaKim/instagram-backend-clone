package com.example.instagram

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class InstagramBackendCloneApplicationTests {

	@Test
	fun applicationTypeExists() {
		assertThat(InstagramBackendCloneApplication::class).isNotNull()
	}
}
