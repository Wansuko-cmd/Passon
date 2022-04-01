package com.wsr.infra.queryservice

import com.wsr.PasswordPairUseCaseModel
import com.wsr.get.FetchPasswordPairUseCaseQueryService
import com.wsr.get.FetchPasswordPairUseCaseQueryServiceException
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
import com.wsr.infra.passworditem.PasswordItemEntityDao
import com.wsr.maybe.Maybe
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.toUseCaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class LocalGetPasswordPairUseCaseQueryServiceImpl(
    private val passwordGroupEntityDao: PasswordGroupEntityDao,
    private val passwordItemEntityDao: PasswordItemEntityDao,
) : FetchPasswordPairUseCaseQueryService {
    override suspend fun getPasswordPair(passwordGroupId: PasswordGroupId): Maybe<PasswordPairUseCaseModel, FetchPasswordPairUseCaseQueryServiceException> = try {
        withContext(Dispatchers.IO) {
            val passwordGroup = async { passwordGroupEntityDao.getById(passwordGroupId.value).toPasswordGroup() }
            val passwordItems = async { passwordItemEntityDao.getAllByPasswordGroupId(passwordGroupId.value).map { it.toPassword() } }
            PasswordPairUseCaseModel(
                passwordGroup = passwordGroup.await().toUseCaseModel(),
                passwordItems = passwordItems.await().map { it.toUseCaseModel() },
            ).let { Maybe.Success(it) }
        }
    } catch (e: Exception) {
        throw e
    }
}