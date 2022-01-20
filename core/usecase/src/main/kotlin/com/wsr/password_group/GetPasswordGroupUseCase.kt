package com.wsr.password_group

import com.wsr.user.Email

class GetPasswordGroupUseCase(
    private val passwordGroupRepository: PasswordGroupRepository
) {
    fun getAllByEmail(email: Email): List<PasswordGroup> = passwordGroupRepository.getAllByEmail(email)
}