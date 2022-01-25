package com.wsr.user

interface UserRepository {
    suspend fun getByEmail(email: Email): User

    suspend fun create(user: User)
}