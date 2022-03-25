package com.wsr.infra.passwordgroup.queryservice

import com.wsr.email.Email
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.getall.GetAllPasswordGroupUseCaseQueryService

class RoomGetAllPasswordGroupUseCaseQueryServiceImpl(
    private val passwordGroupEntityDao: PasswordGroupEntityDao,
) : GetAllPasswordGroupUseCaseQueryService {
    override suspend fun getAllByEmail(email: Email): List<PasswordGroup> = try {
        passwordGroupEntityDao.getAllByEmail(email.value).map { it.toPasswordGroup() }
    } catch (e: Exception) {
        throw GetAllDataFailedException.DatabaseException(e.message ?: "")
    }
}
