package com.wsr.password

import com.wsr.utils.UniqueId

interface PasswordRepository {
    fun getAllByPasswordGroupId(passwordGroupId: UniqueId): List<Password>
    fun create(password: Password)
    fun update(password: Password)
    fun delete(id: UniqueId)
}