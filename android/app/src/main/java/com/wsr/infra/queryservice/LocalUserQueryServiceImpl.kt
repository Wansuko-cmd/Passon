package com.wsr.infra.queryservice

import com.wsr.maybe.Maybe
import com.wsr.queryservice.UserQueryService
import com.wsr.queryservice.UserQueryServiceException
import com.wsr.user.UserId
import com.wsr.user.User

class LocalUserQueryServiceImpl : UserQueryService {
    override suspend fun get(email: UserId): Maybe<User, UserQueryServiceException> {
        TODO("Not yet implemented")
    }
}
