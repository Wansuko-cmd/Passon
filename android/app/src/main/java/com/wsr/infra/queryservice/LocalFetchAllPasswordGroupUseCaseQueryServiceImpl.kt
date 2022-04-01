package com.wsr.infra.queryservice

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.fetch.FetchAllPasswordGroupUseCaseQueryService
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
import com.wsr.toUseCaseModel
import com.wsr.user.Email

class LocalFetchAllPasswordGroupUseCaseQueryServiceImpl(
    private val passwordGroupEntityDao: PasswordGroupEntityDao,
) : FetchAllPasswordGroupUseCaseQueryService {
    override suspend fun getAllPasswordGroup(email: Email): List<PasswordGroupUseCaseModel> = try {
        passwordGroupEntityDao
            .getAllByEmail(email.value)
            .map { it.toPasswordGroup() }
            .map { it.toUseCaseModel() }
    } catch (e: Exception) {
        throw e
    }
}
