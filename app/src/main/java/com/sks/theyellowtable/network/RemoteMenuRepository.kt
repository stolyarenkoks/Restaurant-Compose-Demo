package com.sks.theyellowtable.network

import android.util.Log
import com.sks.theyellowtable.network.model.MenuResponse
import com.sks.theyellowtable.network.model.MenuItemResponse
import io.ktor.client.call.body

interface RemoteMenuRepository {
    suspend fun fetchMenu(): List<MenuItemResponse>
}

class RemoteMenuRepositoryImpl(
    private val apiClient: APIClient
): RemoteMenuRepository {

    override suspend fun fetchMenu(): List<MenuItemResponse> {
        return try {
            val menuEndpoint = "Meta-Mobile-Developer-PC/Working-With-Data-API/main/littleLemonSimpleMenu.json"
            val response = apiClient.get(endpoint = menuEndpoint)
            val menuNetwork = response.body<MenuResponse>()
            menuNetwork.menu
        } catch (e: Exception) {
            Log.e("MenuRepository", "Error fetching menu", e)
            emptyList()
        }
    }
}