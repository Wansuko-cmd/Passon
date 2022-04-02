package com.wsr.infra.passwordgroup

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface PasswordGroupEntityDao {

    @Query("SELECT * FROM password_groups WHERE userId = :userId")
    suspend fun getAllByEmail(userId: String): List<PasswordGroupEntity>

    @Query("SELECT * FROM password_groups WHERE id = :id")
    suspend fun getById(id: String): PasswordGroupEntity

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(passwordGroupEntity: PasswordGroupEntity)

    @Update
    suspend fun update(passwordGroupEntity: PasswordGroupEntity)

    @Query("DELETE FROM password_groups WHERE id=:id")
    suspend fun delete(id: String)
}
