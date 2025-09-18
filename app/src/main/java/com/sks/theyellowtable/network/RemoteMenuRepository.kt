package com.sks.theyellowtable.network

import android.util.Log
import com.sks.theyellowtable.models.Menu
import com.sks.theyellowtable.models.MenuItem
import io.ktor.client.call.body
import io.ktor.client.request.get

interface RemoteMenuRepository {
    suspend fun fetchMenuFromApi(): List<MenuItem>
}

class RemoteMenuRepositoryImpl(
    private val apiClient: APIClient
): RemoteMenuRepository {

    override suspend fun fetchMenuFromApi(): List<MenuItem> {
        return try {
            val menuEndpoint = "Meta-Mobile-Developer-PC/Working-With-Data-API/main/littleLemonSimpleMenu.json"
            val response = apiClient.get(endpoint = menuEndpoint)

            val menuNetwork = response.body<Menu>()
            menuNetwork.menu
        } catch (e: Exception) {
            Log.e("MenuRepository", "Error fetching menu", e)
            emptyList()
        }
    }
}