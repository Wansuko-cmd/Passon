package com.wsr.infra.queryservice

import com.wsr.email.Email
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.fetch.FetchAllPasswordGroupUseCaseQueryService
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
import com.wsr.passwordgroup.PasswordGroup

class LocalFetchAllPasswordGroupUseCaseQueryServiceImpl(
    private val passwordGroupEntityDao: PasswordGroupEntityDao,
) : FetchAllPasswordGroupUseCaseQueryService {
    @Throws(GetAllDataFailedException::class)
    override suspend fun getAllPasswordGroup(email: Email): List<PasswordGroup> =
        passwordGroupEntityDao.getAllByEmail(email.value).map { it.toPasswordGroup() }
}
