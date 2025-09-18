package com.sks.theyellowtable.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sks.theyellowtable.database.model.MenuItemEntity

@Dao
interface MenuItemDao {
    @Query("SELECT * FROM MenuItemEntity")
    fun getAll(): LiveData<List<MenuItemEntity>>

    @Insert(onConflict = OnConflictStrategy.Companion.REPLACE)
    fun insertAll(vararg menuItems: MenuItemEntity)

    @Query("SELECT (SELECT COUNT(*) FROM MenuItemEntity) == 0")
    fun isEmpty(): Boolean
}