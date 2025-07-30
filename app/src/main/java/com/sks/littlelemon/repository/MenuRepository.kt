package com.sks.littlelemon.repository

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import com.sks.littlelemon.database.AppDatabase
import com.sks.littlelemon.database.MenuItemRoom
import com.sks.littlelemon.models.MenuItemNetwork
import com.sks.littlelemon.models.MenuNetwork
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
    
    private val httpClient = HttpClient(Android) {
        install(ContentNegotiation) {
            json(
                Json {
                    ignoreUnknownKeys = true
                    coerceInputValues = true
                },
                contentType = ContentType("text", "plain")
            )
        }
    }
    
    fun initDatabase(context: Context) {
        if (database == null) {
            database = Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "database"
            ).allowMainThreadQueries()
            .build()
        }
    }
    
    fun getAllMenuItems(): LiveData<List<MenuItemRoom>> {
        return database?.menuItemDao()?.getAll() 
            ?: androidx.lifecycle.MutableLiveData(getPreviewMockData())
    }
    
    private fun getPreviewMockData(): List<MenuItemRoom> {
        return listOf(
            MenuItemRoom(1, "Greek Salad", 12.99),
            MenuItemRoom(2, "Bruschetta", 8.50),
            MenuItemRoom(3, "Grilled Fish", 18.99),
            MenuItemRoom(4, "Lemon Dessert", 6.99),
            MenuItemRoom(5, "Pasta", 14.50)
        )
    }
    
    suspend fun fetchMenuFromApi(): List<MenuItemNetwork> {
        return try {
            val response = httpClient.get("https://raw.githubusercontent.com/Meta-Mobile-Developer-PC/Working-With-Data-API/main/littleLemonSimpleMenu.json")
            val menuNetwork = response.body<MenuNetwork>()
            Log.d("MenuRepository", "Loaded ${menuNetwork.menu.size} items from API")
            menuNetwork.menu
        } catch (e: Exception) {
            Log.e("MenuRepository", "Error fetching menu", e)
            emptyList()
        }
    }
    
    fun saveMenuToDatabase(menuItems: List<MenuItemNetwork>) {
        try {
            val menuItemsRoom = menuItems.map { it.toMenuItemRoom() }
            database?.menuItemDao()?.insertAll(*menuItemsRoom.toTypedArray())
            Log.d("MenuRepository", "Saved ${menuItemsRoom.size} items to database")
        } catch (e: Exception) {
            Log.e("MenuRepository", "Error saving to database", e)
        }
    }
    
    fun isDatabaseEmpty(): Boolean {
        return database?.menuItemDao()?.isEmpty() ?: false // Для Preview возвращаем false (база заполнена mock данными)
    }
} 