package com.sks.theyellowtable.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.sks.theyellowtable.database.AppDatabase
import com.sks.theyellowtable.database.model.MenuItemEntity
import com.sks.theyellowtable.models.MenuItem
import com.sks.theyellowtable.models.Menu
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.http.ContentType
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

object MenuRepository {
    private var database: AppDatabase? = null

    fun initDatabase(context: Context) {
        if (database == null) {
            database = Room.databaseBuilder(
                context.applicationContext, AppDatabase::class.java, "database"
            ).allowMainThreadQueries().build()
        }
    }

    fun getAllMenuItems(): LiveData<List<MenuItemEntity>> {
        if (database == null) {
            return androidx.lifecycle.MutableLiveData(getPreviewMockData())
        }
        return database!!.menuItemDao().getAll()
    }

    fun saveMenuToDatabase(menuItems: List<MenuItem>) {
        try {
            val menuItemsRoom = menuItems.map { it.toMenuItemRoom() }
            database?.menuItemDao()?.insertAll(*menuItemsRoom.toTypedArray())
        } catch (e: Exception) {
            Log.e("MenuRepository", "Error saving to database", e)
        }
    }

    fun isDatabaseEmpty(): Boolean {
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