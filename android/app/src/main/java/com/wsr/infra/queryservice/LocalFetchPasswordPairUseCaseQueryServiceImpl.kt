package com.wsr.infra.queryservice

import com.wsr.PasswordPairUseCaseModel
import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.fetch.FetchPasswordPairUseCaseQueryService
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
import com.wsr.infra.passworditem.PasswordItemEntityDao
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.toUseCaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.jvm.Throws

class LocalFetchPasswordPairUseCaseQueryServiceImpl(
    private val passwordGroupEntityDao: PasswordGroupEntityDao,
    private val passwordItemEntityDao: PasswordItemEntityDao,
) : FetchPasswordPairUseCaseQueryService {
    @Throws(GetAllDataFailedException::class)
    override suspend fun getPasswordPair(passwordGroupId: PasswordGroupId): PasswordPairUseCaseModel = try {
        withContext(Dispatchers.IO) {
            val passwordGroup = async { passwordGroupEntityDao.getById(passwordGroupId.value).toPasswordGroup() }
            val passwordItems = async { passwordItemEntityDao.getAllByPasswordGroupId(passwordGroupId.value).map { it.toPassword() } }
            PasswordPairUseCaseModel(
                passwordGroup = passwordGroup.await().toUseCaseModel(),
                passwordItems = passwordItems.await().map { it.toUseCaseModel() },
            )
        }
    } catch (e: Exception) {
        throw GetAllDataFailedException.DatabaseException(e.message ?: "")
    }
}
