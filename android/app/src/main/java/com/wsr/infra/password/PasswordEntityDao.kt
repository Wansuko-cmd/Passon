package com.wsr.infra.password

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PasswordEntityDao {

    @Query("SELECT * FROM passwords WHERE password_group_id = :passwordGroupId")
    suspend fun getAllByPasswordGroupId(passwordGroupId: String): List<PasswordEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(passwordEntity: PasswordEntity)

    @Query("DELETE FROM passwords WHERE id=:id")
    suspend fun delete(id: String)
}
