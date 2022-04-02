package com.wsr.passwordgroup

// class TestPasswordGroupRepositoryImpl : PasswordGroupRepository {
//
//    companion object {
//        internal val data = mutableListOf(
//            PasswordGroup.of(
//                userId = Email.from("userId@example.com"),
//                title = "Title1-1",
//                remark = "Remark1-1"
//            ),
//            PasswordGroup.of(
//                userId = Email.from("userId@example.com"),
//                title = "Title1-2",
//                remark = "Remark1-2"
//            ),
//            PasswordGroup.of(
//                userId = Email.from("userId@example.com"),
//                title = "Title1-3",
//                remark = "Remark1-3"
//            ),
//            PasswordGroup.of(
//                userId = Email.from("userId@example.com"),
//                title = "Title2-1",
//                remark = "Remark2-1"
//            ),
//            PasswordGroup.of(
//                userId = Email.from("userId@example.com"),
//                title = "Title3-1",
//                remark = "Remark3-1"
//            ),
//        )
//    }
//
//    @Throws(GetAllDataFailedException::class)
//    override suspend fun getAllByEmail(userId: Email): List<PasswordGroup> =
//        data.filter { it.userId == userId }
//
//    @Throws(GetDataFailedException::class)
//    override suspend fun getById(id: PasswordGroupId): PasswordGroup =
//        data.first { it.id == id }
//
//    override suspend fun create(passwordGroup: PasswordGroup): PasswordGroup {
//        data.add(passwordGroup)
//        return passwordGroup
//    }
//
//    @Throws(UpdateDataFailedException::class)
//    override suspend fun update(id: PasswordGroupId, title: String, remark: String): PasswordGroup {
//        data.first { it.id == id }.let { oldPasswordGroup ->
//            data.removeIf { it == oldPasswordGroup }
//            val newPasswordGroup = oldPasswordGroup.copy(
//                title = title,
//                remark = remark,
//            )
//            data.add(newPasswordGroup)
//            return newPasswordGroup
//        }
//    }
//
//    override suspend fun delete(id: PasswordGroupId) {
//        data.removeIf { it.id == id }
//    }
// }
