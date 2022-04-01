package com.wsr.fetch

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.maybe.Maybe
import com.wsr.maybe.mapBoth
import com.wsr.user.Email
import kotlinx.coroutines.flow.MutableStateFlow

class FetchAllPasswordGroupUseCaseImpl(
    private val queryService: FetchAllPasswordGroupUseCaseQueryService,
) : FetchAllPasswordGroupUseCase {
    private val _data =
        MutableStateFlow<Maybe<List<PasswordGroupUseCaseModel>, FetchAllPasswordGroupUseCaseException>>(Maybe.Loading)
    override val data get() = _data.asSharedFlow().distinctUntilChanged()

    override suspend fun fetch(email: String) {
        _data.emit(Maybe.Loading)
        val passwordGroups = queryService
            .getAllPasswordGroup(Email(email))
            .mapBoth(
                success = { it },
                failure = { FetchAllPasswordGroupUseCaseException.SystemError(it.message.orEmpty(), it) }
            )

        _data.emit(passwordGroups)
    }
}
