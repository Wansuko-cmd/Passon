package com.wsr.infra

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wsr.infra.password.PasswordEntity
import com.wsr.infra.password.PasswordEntityDao
import com.wsr.infra.passwordgroup.PasswordGroupEntity
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao

@Database(entities = [PasswordEntity::class, PasswordGroupEntity::class], version = 1)
abstract class PassonDatabase : RoomDatabase() {

    abstract fun passwordEntityDao(): PasswordEntityDao
    abstract fun passwordGroupEntityDao(): PasswordGroupEntityDao
}