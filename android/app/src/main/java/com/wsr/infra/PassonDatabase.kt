package com.wsr.infra

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wsr.infra.passwordgroup.PasswordGroupEntity
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
import com.wsr.infra.passworditem.PasswordItemEntity
import com.wsr.infra.passworditem.PasswordItemEntityDao
import com.wsr.infra.user.UserEntity
import com.wsr.infra.user.UserEntityDao

@Database(entities = [PasswordItemEntity::class, PasswordGroupEntity::class, UserEntity::class], version = 1)
abstract class PassonDatabase : RoomDatabase() {
    abstract fun passwordGroupEntityDao(): PasswordGroupEntityDao
    abstract fun passwordItemEntityDao(): PasswordItemEntityDao
    abstract fun userEntityDao(): UserEntityDao
}
