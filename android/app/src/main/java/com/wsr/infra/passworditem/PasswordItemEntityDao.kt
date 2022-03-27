package com.wsr.infra.passworditem

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PasswordItemEntityDao {

    @Query("SELECT * FROM password_items WHERE id = :id")
    suspend fun getById(id: String): PasswordItemEntity

    @Query("SELECT * FROM password_items WHERE password_group_id = :passwordGroupId")
    suspend fun getAllByPasswordGroupId(passwordGroupId: String): List<PasswordItemEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(passwordItemEntity: PasswordItemEntity)

    @Query("DELETE FROM password_items WHERE id=:id")
    suspend fun delete(id: String)

    @Query("DELETE FROM password_items WHERE password_group_id=:passwordGroupId")
    suspend fun deleteAllByPasswordGroupId(passwordGroupId: String)
}
