package com.wsr.infra.queryservice

import com.wsr.maybe.Maybe
import com.wsr.queryservice.UserQueryService
import com.wsr.queryservice.UserQueryServiceException
import com.wsr.user.User
import com.wsr.user.UserId

class LocalUserQueryServiceImpl : UserQueryService {
    override suspend fun get(userId: UserId): Maybe<User, UserQueryServiceException> {
        TODO("Not yet implemented")
    }
}
