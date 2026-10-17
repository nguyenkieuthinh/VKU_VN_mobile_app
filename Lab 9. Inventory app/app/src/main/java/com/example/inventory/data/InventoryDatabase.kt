package com.example.inventory.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

/**
 * Database cua ung dung Inventory, chua bang "items".
 *
 * Chi tao mot instance duy nhat cho ca app (xem [getDatabase]) vi moi lan goi
 * Room.databaseBuilder deu mo mot ket noi moi toi file SQLite.
 */
@Database(entities = [Item::class], version = 1, exportSchema = false)
abstract class InventoryDatabase : RoomDatabase() {

    /** Cho phep truy cap cac ham truy van cua bang "items". */
    abstract fun itemDao(): ItemDao

    companion object {
        @Volatile private var Instance: InventoryDatabase? = null

        /**
         * Tra ve instance duy nhat cua database, tao moi neu chua co.
         *
         * [synchronized] de nhieu luong cung goi ham nay cung luc cung chi tao mot instance.
         */
        fun getDatabase(context: Context): InventoryDatabase {
            return Instance
                    ?: synchronized(this) {
                        Room.databaseBuilder(
                                        context,
                                        InventoryDatabase::class.java,
                                        "item_database"
                                )
                                .build()
                                .also { Instance = it }
                    }
        }
    }
}
