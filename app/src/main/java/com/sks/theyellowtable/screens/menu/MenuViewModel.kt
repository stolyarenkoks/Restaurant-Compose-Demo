package com.sks.theyellowtable.screens.menu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sks.theyellowtable.database.model.MenuItemEntity
import com.sks.theyellowtable.network.RemoteMenuRepository
import com.sks.theyellowtable.repository.MenuRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuViewModel(
    private val remoteMenuRepository: RemoteMenuRepository
) : ViewModel() {
    
    val menuItems: LiveData<List<MenuItemEntity>> = MenuRepository.getAllMenuItems()
    
    init {
        loadMenuData()
    }
    
    private fun loadMenuData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (MenuRepository.isDatabaseEmpty()) {
                    val apiMenuItems = remoteMenuRepository.fetchMenuFromApi()
                    MenuRepository.saveMenuToDatabase(apiMenuItems)
                } else {
                    Log.d("MenuViewModel", "Database already has data, skipping API fetch")
                }
            } catch (e: Exception) {
                Log.e("MenuViewModel", "Error in loadMenuData", e)
            }
        }
    }
}