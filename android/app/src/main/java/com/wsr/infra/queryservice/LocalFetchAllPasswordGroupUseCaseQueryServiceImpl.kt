package com.wsr.infra.queryservice

import com.wsr.PasswordGroupUseCaseModel
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.fetch.FetchAllPasswordGroupUseCaseQueryService
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
import com.wsr.toUseCaseModel
import com.wsr.user.Email

class LocalFetchAllPasswordGroupUseCaseQueryServiceImpl(
    private val passwordGroupEntityDao: PasswordGroupEntityDao,
) : FetchAllPasswordGroupUseCaseQueryService {
    @Throws(GetAllDataFailedException::class)
    override suspend fun getAllPasswordGroup(email: Email): List<PasswordGroupUseCaseModel> = try {
        passwordGroupEntityDao
            .getAllByEmail(email.value)
            .map { it.toPasswordGroup() }
            .map { it.toUseCaseModel() }
    } catch (e: Exception) {
        throw GetAllDataFailedException.DatabaseException(e.message ?: "")
    }
}
