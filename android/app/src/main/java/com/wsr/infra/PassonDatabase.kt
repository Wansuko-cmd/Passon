package com.wsr.infra

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wsr.infra.password.PasswordPairEntity
import com.wsr.infra.password.PasswordPairEntityDao
import com.wsr.infra.passwordgroup.PasswordGroupEntity
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao

@Database(entities = [PasswordPairEntity::class, PasswordGroupEntity::class], version = 1)
abstract class PassonDatabase : RoomDatabase() {
    abstract fun passwordEntityDao(): PasswordPairEntityDao
    abstract fun passwordGroupEntityDao(): PasswordGroupEntityDao
}
