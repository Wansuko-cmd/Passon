package com.wsr.infra.queryservice

import com.wsr.infra.user.UserEntityDao
import com.wsr.maybe.Maybe
import com.wsr.queryservice.UsersQueryService
import com.wsr.queryservice.UsersQueryServiceException
import com.wsr.user.User

class LocalUsersQueryServiceImpl(private val userEntityDao: UserEntityDao) : UsersQueryService {
    override suspend fun getAll(): Maybe<List<User>, UsersQueryServiceException> = try {
        userEntityDao
            .getAll()
            .map { it.toUser() }
            .let { Maybe.Success(it) }
    } catch (e: Exception) {
        Maybe.Failure(UsersQueryServiceException.SystemError(e.message.orEmpty(), e))
    }
}