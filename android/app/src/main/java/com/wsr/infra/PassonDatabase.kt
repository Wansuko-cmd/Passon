package com.wsr.infra

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.wsr.infra.password.PasswordEntity
import com.wsr.infra.password.PasswordEntityDao
import com.wsr.infra.passwordgroup.PasswordGroupEntity
import com.wsr.infra.passwordgroup.PasswordGroupEntityDao

@Database(entities = [PasswordEntity::class, PasswordGroupEntity::class], version = 1)
abstract class PassonDatabase : RoomDatabase() {

    abstract fun passwordEntityDao(): PasswordEntityDao
    abstract fun passwordGroupEntityDao(): PasswordGroupEntityDao

    companion object {
        @Volatile
        private var INSTANCE: PassonDatabase? = null

        fun getDatabase(context: Context) : PassonDatabase {
            if(INSTANCE != null) return INSTANCE as PassonDatabase
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PassonDatabase::class.java,
                    "passon_database"
                ).build()

                INSTANCE = instance
                return instance
            }
        }
    }
}