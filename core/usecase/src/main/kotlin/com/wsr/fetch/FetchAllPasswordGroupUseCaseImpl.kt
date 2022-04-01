package com.wsr.fetch

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.state.State
import com.wsr.state.consume
import com.wsr.state.mapBoth
import com.wsr.user.Email
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.distinctUntilChanged

class FetchAllPasswordGroupUseCaseImpl(
    private val queryService: FetchAllPasswordGroupUseCaseQueryService,
) : FetchAllPasswordGroupUseCase {
    private val _data =
        MutableStateFlow<State<List<PasswordGroupUseCaseModel>, FetchAllPasswordGroupUseCaseException>>(State.Loading)
    override val data get() = _data.asSharedFlow().distinctUntilChanged()

    override suspend fun fetch(email: String) {
        _data.emit(State.Loading)
        val passwordGroups = queryService
            .getAllPasswordGroup(Email(email))
            .mapBoth(
                success = { it },
                failure = { FetchAllPasswordGroupUseCaseException.SystemError(it.message.orEmpty(), it) }
            )

        _data.emit(passwordGroups)
    }
}
