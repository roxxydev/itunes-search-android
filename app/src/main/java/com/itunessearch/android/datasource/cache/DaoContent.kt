package com.itunessearch.android.datasource.cache

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.itunessearch.android.datasource.model.EntityCacheContent

@Dao
interface DaoContent:
    BaseDao<EntityCacheContent> {

    // Order cache in descending so that cache items will be displayed starting from recent search.
    @Query("SELECT * FROM content ORDER BY id DESC LIMIT 50")
    suspend fun getAll(): List<EntityCacheContent>

    @Query("SELECT * FROM content WHERE id=:id OR trackId=:trackId")
    suspend fun get(id: Int?, trackId: Int?): EntityCacheContent

    @Transaction
    suspend fun upsert(obj: EntityCacheContent) {
        val id = insert(obj)
        if (id == -1L) {
            update(obj)
        }
    }

    @Transaction
    suspend fun upsert(objList: List<EntityCacheContent>) {
        val insertResult: List<Long> = insert(objList)
        val updateList: MutableList<EntityCacheContent> = ArrayList()
        for (i in insertResult.indices) if (insertResult[i] == -1L) {
            updateList.add(objList[i])
        }
        if (!updateList.isEmpty()) {
            update(updateList)
        }
    }
}
