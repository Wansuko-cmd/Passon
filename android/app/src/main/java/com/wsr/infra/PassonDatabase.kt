package com.wsr.infra

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wsr.infra.passwordgroup.PasswordGroupEntity
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao
import com.wsr.infra.passworditem.PasswordItemEntity
import com.wsr.infra.passworditem.PasswordItemEntityDao

@Database(entities = [PasswordItemEntity::class, PasswordGroupEntity::class], version = 1)
abstract class PassonDatabase : RoomDatabase() {
    abstract fun passwordEntityDao(): PasswordItemEntityDao
    abstract fun passwordGroupEntityDao(): PasswordGroupEntityDao
}
