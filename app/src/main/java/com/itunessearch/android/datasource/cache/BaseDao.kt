package com.itunessearch.android.datasource.cache

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

@Dao
interface BaseDao<T> {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(obj: T): Long

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    fun insert(obj: List<T>): List<Long>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(obj: T)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(obj: List<T>?)
}
