package com.wsr.infra.queryservice

import com.wsr.infra.user.UserEntityDao
import com.wsr.maybe.Maybe
import com.wsr.queryservice.UserQueryService
import com.wsr.queryservice.UserQueryServiceException
import com.wsr.user.User
import com.wsr.user.UserId

class LocalUserQueryServiceImpl(private val userEntityDao: UserEntityDao) : UserQueryService {
    override suspend fun get(userId: UserId): Maybe<User, UserQueryServiceException> = try {
        userEntityDao.getById(userId.value).toUser().let { Maybe.Success(it) }
    } catch (e: Exception) {
        Maybe.Failure(UserQueryServiceException.SystemError(e.message.orEmpty(), e))
    }
}
