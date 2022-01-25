package com.wsr.user

import com.wsr.image.Base64
import com.wsr.image.Image
import com.wsr.utils.UniqueId

class TestUserRepositoryImpl : UserRepository {

    companion object {
        internal val data = mutableListOf(
            User(
                Email("example1@gmail.com"),
                DisplayName("example1"),
                Image(UniqueId("image1"), Base64("Content"))
            ),
            User(
                Email("example2@gmail.com"),
                DisplayName("example2"),
                Image(UniqueId("image1"), Base64("Content"))
            ),
            User(
                Email("example3@gmail.com"),
                DisplayName("example3"),
                Image(UniqueId("image1"), Base64("Content"))
            ),
        )
    }

    override suspend fun getByEmail(email: Email): User = data.first { it.email == email }

    override suspend fun create(user: User) {
        data.add(user)
    }
}