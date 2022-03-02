package com.rachel.alarmapp.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [Alarm::class],
    version = 2
)

abstract class AlarmDB : RoomDatabase(){
    abstract fun alarmDao(): AlarmDao

    companion object{
        @Volatile
        private var instance: AlarmDB? = null
        private var LOCK = Any()

        operator fun invoke(context: Context) =
            Room.databaseBuilder(context.applicationContext, AlarmDB::class.java, "alarm12345.db")
                .fallbackToDestructiveMigration()
                .build()
    }
}
