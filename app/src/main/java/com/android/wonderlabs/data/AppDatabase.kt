package com.android.wonderlabs.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.android.wonderlabs.model.VolumeQuarterModel
import com.android.wonderlabs.utils.TABLE_NAME

@Database(
    version = 1,
    entities = [VolumeQuarterModel::class],
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun volumeDao(): VolumeDao

    fun clearAll() = clearAllTables()

    companion object {
        fun create(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, TABLE_NAME)
                .allowMainThreadQueries() // allow querying on MainThread (this useful in some cases)
                .fallbackToDestructiveMigration() //  this mean that it will delete all tables and recreate them after version is changed
                .build()
        }
    }
}