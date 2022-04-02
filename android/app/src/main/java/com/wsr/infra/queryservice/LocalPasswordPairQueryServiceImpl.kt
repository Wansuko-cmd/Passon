package com.wsr.infra.queryservice

import com.wsr.PasswordPairUseCaseModel
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
import com.wsr.infra.passworditem.PasswordItemEntityDao
import com.wsr.maybe.Maybe
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.queryservice.PasswordPairQueryService
import com.wsr.queryservice.PasswordPairQueryServiceException
import com.wsr.toUseCaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class LocalPasswordPairQueryServiceImpl(
    private val passwordGroupEntityDao: PasswordGroupEntityDao,
    private val passwordItemEntityDao: PasswordItemEntityDao,
) : PasswordPairQueryService {
    override suspend fun get(passwordGroupId: PasswordGroupId): Maybe<PasswordPairUseCaseModel, PasswordPairQueryServiceException> = try {
        withContext(Dispatchers.IO) {
            val passwordGroup = async { passwordGroupEntityDao.getById(passwordGroupId.value).toPasswordGroup() }
            val passwordItems = async { passwordItemEntityDao.getAllByPasswordGroupId(passwordGroupId.value).map { it.toPassword() } }
            PasswordPairUseCaseModel(
                passwordGroup = passwordGroup.await().toUseCaseModel(),
                passwordItems = passwordItems.await().map { it.toUseCaseModel() },
            ).let { Maybe.Success(it) }
        }
    } catch (e: Exception) {
        Maybe.Failure(PasswordPairQueryServiceException.DatabaseError(e.message.orEmpty()))
    }
}
