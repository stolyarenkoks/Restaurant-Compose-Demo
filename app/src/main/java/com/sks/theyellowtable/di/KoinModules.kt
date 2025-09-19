package com.sks.theyellowtable.di

import com.sks.theyellowtable.network.APIClient
import com.sks.theyellowtable.network.APIClientImpl
import com.sks.theyellowtable.network.RemoteMenuRepository
import com.sks.theyellowtable.network.RemoteMenuRepositoryImpl
import com.sks.theyellowtable.repository.CartRepository
import com.sks.theyellowtable.repository.CartRepositoryImpl
import com.sks.theyellowtable.repository.DishRepository
import com.sks.theyellowtable.repository.DishRepositoryImpl
import com.sks.theyellowtable.repository.MenuRepository
import com.sks.theyellowtable.repository.MenuRepositoryImpl
import com.sks.theyellowtable.screens.cart.CartViewModel
import com.sks.theyellowtable.screens.dishDetails.DishDetailsViewModel
import com.sks.theyellowtable.screens.home.HomeViewModel
import com.sks.theyellowtable.screens.login.LoginViewModel
import com.sks.theyellowtable.screens.menu.MenuViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val networkModule = module {
    single<APIClient> { APIClientImpl() }
    single<RemoteMenuRepository> { RemoteMenuRepositoryImpl(get()) }
}

val repositoryModule = module {
    single<DishRepository> { DishRepositoryImpl() }
    single<CartRepository> { CartRepositoryImpl }
    single<MenuRepository> { 
        MenuRepositoryImpl().apply { 
            initDatabase(androidContext()) 
        } 
    }
}

val viewModelModule = module {
    viewModel { HomeViewModel(get()) }
    viewModel { MenuViewModel(get(), get()) }
    viewModel { CartViewModel(get()) }
    viewModel { DishDetailsViewModel(get(), get()) }
    viewModel { LoginViewModel() }
}

val appModules = listOf(
    networkModule,
    repositoryModule,
    viewModelModule
)
