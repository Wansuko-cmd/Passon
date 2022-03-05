package com.wsr.infra.passwordgroup

import androidx.room.*

@Dao
interface PasswordGroupEntityDao {

    @Query("SELECT * FROM password_groups WHERE email = :email")
    suspend fun getAllByEmail(email: String): List<PasswordGroupEntity>

    @Query("SELECT * FROM password_groups WHERE id = :id")
    suspend fun getById(id: String): PasswordGroupEntity

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(passwordGroupEntity: PasswordGroupEntity)

    @Query("UPDATE password_groups SET title = :title, remark = :remark WHERE id = :id")
    suspend fun update(id: String, title: String, remark: String)

    @Query("DELETE FROM password_groups WHERE id=:id")
    suspend fun delete(id: String)
}