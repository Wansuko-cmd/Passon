package com.wsr.infra.password

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface PasswordPairEntityDao {

    @Query("SELECT * FROM password_pairs WHERE password_group_id = :passwordGroupId")
    suspend fun getAllByPasswordGroupId(passwordGroupId: String): List<PasswordPairEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(passwordPairEntity: PasswordPairEntity)

    @Query("DELETE FROM password_pairs WHERE id=:id")
    suspend fun delete(id: String)
}
