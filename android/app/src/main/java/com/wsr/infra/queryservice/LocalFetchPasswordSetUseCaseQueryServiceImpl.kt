package com.wsr.infra.queryservice

import com.wsr.exceptions.GetAllDataFailedException
import com.wsr.fetch.FetchPasswordSetUseCaseQueryService
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
import com.wsr.infra.passworditem.PasswordItemEntityDao
import com.wsr.passwordgroup.PasswordGroup
import com.wsr.passwordgroup.PasswordGroupId
import com.wsr.passworditem.PasswordItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import kotlin.jvm.Throws

class LocalFetchPasswordSetUseCaseQueryServiceImpl(
    private val passwordGroupEntityDao: PasswordGroupEntityDao,
    private val passwordItemEntityDao: PasswordItemEntityDao,
) : FetchPasswordSetUseCaseQueryService {
    @Throws(GetAllDataFailedException::class)
    override suspend fun getPasswordSet(passwordGroupId: PasswordGroupId): Pair<PasswordGroup, List<PasswordItem>> = withContext(Dispatchers.IO) {
        val passwordGroup = async { passwordGroupEntityDao.getById(passwordGroupId.value).toPasswordGroup() }
        val passwordItems = async { passwordItemEntityDao.getAllByPasswordGroupId(passwordGroupId.value).map { it.toPassword() } }
        passwordGroup.await() to passwordItems.await()
    }
}
