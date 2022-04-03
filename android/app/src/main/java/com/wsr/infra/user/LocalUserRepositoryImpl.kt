package com.wsr.infra.user

import com.wsr.exceptions.CreateDataFailedException
import com.wsr.exceptions.UpdateDataFailedException
import com.wsr.maybe.Maybe
import com.wsr.user.DisplayName
import com.wsr.user.LoginPassword
import com.wsr.user.User
import com.wsr.user.UserId
import com.wsr.user.UserRepository

class LocalUserRepositoryImpl(private val userEntityDao: UserEntityDao) : UserRepository {
    override suspend fun create(user: User): Maybe<Unit, CreateDataFailedException> = try {
        userEntityDao.insert(user.toEntity())
        Maybe.Success(Unit)
    } catch (e: Exception) {
        Maybe.Failure(CreateDataFailedException.DatabaseError(e.message.orEmpty()))
    }

    override suspend fun update(
        userId: UserId,
        displayName: DisplayName,
    ): Maybe<Unit, UpdateDataFailedException> = try {
        userEntityDao.getById(userId.value)
            .copy(displayName = displayName.value)
            .also { userEntityDao.update(it) }
        Maybe.Success(Unit)
    } catch (e: Exception) {
        Maybe.Failure(UpdateDataFailedException.DatabaseError(e.message.orEmpty()))
    }

    override suspend fun update(
        userId: UserId,
        loginPassword: LoginPassword.HashedLoginPassword,
    ): Maybe<Unit, UpdateDataFailedException> = try {
        userEntityDao.getById(userId.value)
            .copy(loginPassword = loginPassword.value)
            .also { userEntityDao.update(it) }
        Maybe.Success(Unit)
    } catch (e: Exception) {
        Maybe.Failure(UpdateDataFailedException.DatabaseError(e.message.orEmpty()))
    }
}
