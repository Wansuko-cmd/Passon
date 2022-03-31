package com.wsr.user

interface UserRepository {
    suspend fun create(user: User)
    suspend fun update(user: User)
}
