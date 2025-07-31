package com.sks.littlelemon.screens.menu

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sks.littlelemon.database.MenuItemRoom
import com.sks.littlelemon.repository.MenuRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuViewModel : ViewModel() {
    
    val menuItems: LiveData<List<MenuItemRoom>> = MenuRepository.getAllMenuItems()
    
    init {
        Log.d("MenuViewModel", "ViewModel initialized")
        loadMenuData()
    }
    
    private fun loadMenuData() {
        Log.d("MenuViewModel", "Starting loadMenuData()")
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (MenuRepository.isDatabaseEmpty()) {
                    Log.d("MenuViewModel", "Database is empty, fetching from API")
                    val apiMenuItems = MenuRepository.fetchMenuFromApi()
                    Log.d("MenuViewModel", "Fetched ${apiMenuItems.size} items from API")
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