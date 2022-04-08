package com.wsr.infra.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserEntityDao {

    @Query("SELECT * FROM users")
    suspend fun getAll(): List<UserEntity>

    @Query("SELECT * FROM users WHERE id = :userId")
    suspend fun getById(userId: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(userEntity: UserEntity)

    @Update
    suspend fun update(userEntity: UserEntity)

    @Query("DELETE FROM users WHERE id=:id")
    suspend fun delete(id: String)
}
