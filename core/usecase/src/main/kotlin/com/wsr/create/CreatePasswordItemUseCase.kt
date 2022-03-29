package com.wsr.create

import com.wsr.PasswordItemUseCaseModel

interface CreatePasswordItemUseCase {
    // 副作用なし（いい命名が思いつき変更する予定）
    fun createPasswordItemInstance(passwordGroupId: String): PasswordItemUseCaseModel
}
