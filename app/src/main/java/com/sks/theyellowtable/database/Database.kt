package com.sks.theyellowtable.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sks.theyellowtable.database.dao.MenuItemDao
import com.sks.theyellowtable.database.model.MenuItemEntity

@Database(entities = [MenuItemEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun menuItemDao(): MenuItemDao
}
