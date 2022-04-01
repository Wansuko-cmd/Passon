package com.wsr.get

import com.wsr.maybe.mapBoth
import com.wsr.user.Email

class GetAllPasswordGroupUseCaseImpl(
    private val queryService: FetchAllPasswordGroupUseCaseQueryService,
) : GetAllPasswordGroupUseCase {

    override suspend fun get(email: String) =
        queryService
            .getAllPasswordGroup(Email(email))
            .mapBoth(
                success = { it },
                failure = {
                    GetAllPasswordGroupUseCaseException.SystemError(it.message.orEmpty(), it)
                }
            )
}
