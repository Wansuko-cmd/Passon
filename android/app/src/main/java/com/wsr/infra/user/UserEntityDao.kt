package com.wsr.infra.user

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface UserEntityDao {

    @Query("SELECT * FROM user WHERE userId = :userId")
    suspend fun getByEmail(userId: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insert(userEntity: UserEntity)

    @Update
    suspend fun update(userEntity: UserEntity)
}
