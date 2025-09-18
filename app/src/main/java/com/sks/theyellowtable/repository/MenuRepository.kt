package com.sks.theyellowtable.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.sks.theyellowtable.database.AppDatabase
import com.sks.theyellowtable.database.model.MenuItemEntity
import com.sks.theyellowtable.network.model.MenuItemResponse

interface MenuRepository {
    fun initDatabase(context: Context)
    fun getAllMenuItems(): LiveData<List<MenuItemEntity>>
    fun saveMenuToDatabase(menuItems: List<MenuItemResponse>)
    fun isDatabaseEmpty(): Boolean
}

class MenuRepositoryImpl: MenuRepository {
    private var database: AppDatabase? = null

    override fun initDatabase(context: Context) {
        if (database == null) {
            database = Room.databaseBuilder(
                context.applicationContext, AppDatabase::class.java, "database"
            ).allowMainThreadQueries().build()
        }
    }

    override fun getAllMenuItems(): LiveData<List<MenuItemEntity>> {
        if (database == null) {
            return androidx.lifecycle.MutableLiveData(getPreviewMockData())
        }
        return database!!.menuItemDao().getAll()
    }

    override fun saveMenuToDatabase(menuItems: List<MenuItemResponse>) {
        try {
            val menuItemsRoom = menuItems.map { it.toMenuItemEntity() }
            database?.menuItemDao()?.insertAll(*menuItemsRoom.toTypedArray())
        } catch (e: Exception) {
            Log.e("MenuRepository", "Error saving to database", e)
        }
    }

    override fun isDatabaseEmpty(): Boolean {
        return database?.menuItemDao()?.isEmpty() ?: true
    }

    // MARK: - Private Methods

    private fun getPreviewMockData(): List<MenuItemEntity> {
        return listOf(
            MenuItemEntity(1, "Greek Salad", 12.99),
            MenuItemEntity(2, "Bruschetta", 8.50),
            MenuItemEntity(3, "Grilled Fish", 18.99),
        )
    }
}