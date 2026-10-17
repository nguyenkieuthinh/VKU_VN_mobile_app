package com.example.inventory.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

/**
 * Data Access Object for the "items" table.
 *
 * Room sinh phan cai dat cua interface nay luc build. Cac ham doc tra ve [Flow] nen UI tu dong
 * duoc cap nhat moi khi bang "items" thay doi.
 */
@Dao
interface ItemDao {

    /** Them mot item moi. Neu id da ton tai thi bo qua, khong ghi de. */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: Item)

    /** Cap nhat item da co. */
    @Update
    suspend fun update(item: Item)

    /** Xoa item. */
    @Delete
    suspend fun delete(item: Item)

    /** Lay mot item theo [id]. */
    @Query("SELECT * from items WHERE id = :id")
    fun getItem(id: Int): Flow<Item>

    /** Lay toan bo item, sap xep theo ten tang dan. */
    @Query("SELECT * from items ORDER BY name ASC")
    fun getAllItems(): Flow<List<Item>>
}
