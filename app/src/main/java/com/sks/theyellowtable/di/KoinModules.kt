package com.sks.theyellowtable.di

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import com.sks.theyellowtable.database.AppDatabase
import com.sks.theyellowtable.database.dao.MenuItemDao
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
import com.sks.theyellowtable.screens.profile.ProfileViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val databaseModule = module {
    single<AppDatabase> {
        val context = get<Context>()

        Room.databaseBuilder(
            context.applicationContext, AppDatabase::class.java, "database"
        )
            .allowMainThreadQueries()
            .build()
    }
    single<MenuItemDao> { get<AppDatabase>().menuItemDao() }
}

val networkModule = module {
    single<APIClient> { APIClientImpl() }
    single<RemoteMenuRepository> { RemoteMenuRepositoryImpl(get()) }
}

val repositoryModule = module {
    single<DishRepository> { DishRepositoryImpl() }
    single<CartRepository> { CartRepositoryImpl() }
    single<MenuRepository> { MenuRepositoryImpl(get()) }
}

val viewModelModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::MenuViewModel)
    viewModelOf(::CartViewModel)
    viewModelOf(::DishDetailsViewModel)
    viewModelOf(::LoginViewModel)
    viewModelOf(::ProfileViewModel)
}

val appModules = listOf(
    databaseModule,
    networkModule,
    repositoryModule,
    viewModelModule
)
