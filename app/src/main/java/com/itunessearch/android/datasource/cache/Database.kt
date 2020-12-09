package com.itunessearch.android.datasource.cache

import androidx.room.Database
import androidx.room.RoomDatabase
import com.itunessearch.android.datasource.model.EntityCacheContent

@Database(entities = [EntityCacheContent::class], version = 1)
abstract class Database: RoomDatabase() {

    abstract fun daoContent(): DaoContent

    companion object {

        val DATABASE_NAME: String = "db_app"
    }
}
