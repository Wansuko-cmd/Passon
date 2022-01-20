package com.wsr.user

interface UserRepository {
    fun getByEmail(email: Email): User

    fun create(user: User)
}