package com.wsr.passwordgroup

import com.wsr.exceptions.CreateDataFailedException
import com.wsr.exceptions.DeleteDataFailedException
import com.wsr.exceptions.UpdateDataFailedException
import com.wsr.state.State

interface PasswordGroupRepository {
    suspend fun create(passwordGroup: PasswordGroup): State<Unit, CreateDataFailedException>

    suspend fun update(id: PasswordGroupId, title: String, remark: String): State<Unit, UpdateDataFailedException>

    suspend fun delete(id: PasswordGroupId): State<Unit, DeleteDataFailedException>
}
