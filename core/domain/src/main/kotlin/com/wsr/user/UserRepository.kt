package com.wsr.user

import com.wsr.exceptions.CreateDataFailedException
import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.exceptions.UpdateDataFailedException
import com.wsr.maybe.Maybe

interface UserRepository {
    suspend fun create(user: User): Maybe<Unit, CreateDataFailedException>
    suspend fun update(userId: UserId, displayName: DisplayName): Maybe<Unit, UpdateDataFailedException>
    suspend fun update(userId: UserId, loginPassword: LoginPassword.HashedLoginPassword): Maybe<Unit, UpdateDataFailedException>
    suspend fun delete(userId: UserId): Maybe<Unit, DeleteDataFailedException>
}
