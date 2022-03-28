package com.wsr.infra.queryservice

import com.wsr.delete.DeletePasswordGroupUseCaseQueryService
import com.wsr.exceptions.GetDataFailedException
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupId

class LocalDeletePasswordGroupUseCaseQueryImpl(
    private val passwordGroupEntityDao: PasswordGroupEntityDao,
) : DeletePasswordGroupUseCaseQueryService {
    override suspend fun getPasswordGroup(passwordGroupId: PasswordGroupId): PasswordGroup = try {
        passwordGroupEntityDao.getById(passwordGroupId.value).toPasswordGroup()
    } catch (e: NullPointerException) {
        throw GetDataFailedException.NoSuchElementException(e.message ?: "")
    } catch (e: Exception) {
        throw GetDataFailedException.DatabaseException(e.message ?: "")
    }
}
