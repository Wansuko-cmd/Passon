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

    @Update
    suspend fun update(passwordGroupEntity: PasswordGroupEntity)

    @Query("DELETE FROM password_groups WHERE id=:id")
    suspend fun delete(id: String)
}