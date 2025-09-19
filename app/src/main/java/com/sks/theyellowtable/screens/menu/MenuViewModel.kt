package com.sks.theyellowtable.screens.menu

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sks.theyellowtable.database.model.MenuItemEntity
import com.sks.theyellowtable.network.RemoteMenuRepository
import com.sks.theyellowtable.repository.MenuRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MenuViewModel(
    private val remoteMenuRepository: RemoteMenuRepository,
    private val localMenuRepository: MenuRepository
) : ViewModel() {
    
    val menuItems: LiveData<List<MenuItemEntity>> = localMenuRepository.getAllMenuItems()
    
    init {
        loadMenuData()
    }
    
    private fun loadMenuData() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                if (localMenuRepository.isDatabaseEmpty()) {
                    val apiMenuItems = remoteMenuRepository.fetchMenu()
                    localMenuRepository.saveMenuToDatabase(apiMenuItems)
                }
            } catch (e: Exception) {
                // Error handling without logging
            }
        }
    }
}