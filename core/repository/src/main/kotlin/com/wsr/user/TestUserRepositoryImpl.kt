package com.wsr.user

class TestUserRepositoryImpl : UserRepository {

    companion object {
        internal val data = mutableListOf(
            User(
                Email("example1@gmail.com"),
                DisplayName("example1"),
                Image("image1")
            ),
            User(
                Email("example2@gmail.com"),
                DisplayName("example2"),
                Image("image2")
            ),
            User(
                Email("example3@gmail.com"),
                DisplayName("example3"),
                Image("image3")
            ),
        )
    }

    override fun getByEmail(email: Email): User = data.first { it.email == email }

    override fun create(user: User) {
        data.add(user)
    }
}