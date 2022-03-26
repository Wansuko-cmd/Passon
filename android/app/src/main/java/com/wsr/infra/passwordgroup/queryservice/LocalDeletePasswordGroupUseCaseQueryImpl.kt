package com.wsr.infra.passwordgroup.queryservice

import com.wsr.exceptions.GetDataFailedException
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passwordgroup.delete.DeletePasswordGroupUseCaseQueryService

class LocalDeletePasswordGroupUseCaseQueryImpl(
    private val passwordGroupEntityDao: PasswordGroupEntityDao,
) : DeletePasswordGroupUseCaseQueryService {
    override suspend fun getById(passwordGroupId: PasswordGroupId): PasswordGroup = try {
        passwordGroupEntityDao.getById(passwordGroupId.value).toPasswordGroup()
    } catch (e: NullPointerException) {
        throw GetDataFailedException.NoSuchElementException(e.message ?: "")
    } catch (e: Exception) {
        throw GetDataFailedException.DatabaseException(e.message ?: "")
    }
}
