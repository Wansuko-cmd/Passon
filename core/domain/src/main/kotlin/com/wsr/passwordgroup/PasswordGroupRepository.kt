package com.wsr.passwordgroup

import com.wsr.exceptions.CreateDataFailedException
import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.exceptions.UpdateDataFailedException
import com.wsr.maybe.Maybe

interface PasswordGroupRepository {
    suspend fun create(passwordGroup: PasswordGroup): Maybe<Unit, CreateDataFailedException>

    suspend fun update(id: PasswordGroupId, title: String, remark: String): Maybe<Unit, UpdateDataFailedException>

    suspend fun delete(id: PasswordGroupId): Maybe<Unit, DeleteDataFailedException>
}
