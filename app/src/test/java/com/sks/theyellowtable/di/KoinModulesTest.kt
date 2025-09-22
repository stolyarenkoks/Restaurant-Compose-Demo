package com.sks.theyellowtable.di

import android.content.Context
import com.sks.theyellowtable.network.APIClient
import com.sks.theyellowtable.network.RemoteMenuRepository
import com.sks.theyellowtable.repository.CartRepository
import com.sks.theyellowtable.repository.DishRepository
import com.sks.theyellowtable.repository.MenuRepository
import com.sks.theyellowtable.screens.cart.CartViewModel
import com.sks.theyellowtable.screens.dishDetails.DishDetailsViewModel
import com.sks.theyellowtable.screens.home.HomeViewModel
import com.sks.theyellowtable.screens.login.LoginViewModel
import com.sks.theyellowtable.screens.menu.MenuViewModel
import io.mockk.mockk
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import org.koin.test.inject
import org.koin.test.check.checkModules
import kotlin.test.assertNotNull
import kotlin.test.assertNotSame
import kotlin.test.assertSame

class KoinModulesTest: KoinTest {

    private val mockContext: Context = mockk(relaxed = true)

    @Before
    fun setup() {
        // Given
        try {
            stopKoin()
        } catch (e: Exception) {
            // Ignore if Koin was not started
        }
        
        startKoin {
            androidContext(mockContext)
            modules(appModules)
        }
    }

    @After
    fun tearDown() {
        try {
            stopKoin()
        } catch (e: Exception) {
            // Ignore if Koin is already stopped
        }
    }

    @Test
    fun `koin modules configuration is valid`() {
        // When
        val apiClient: APIClient by inject()
        val dishRepository: DishRepository by inject()
        val cartRepository: CartRepository by inject()
        
        // Then
        assertNotNull(apiClient)
        assertNotNull(dishRepository)
        assertNotNull(cartRepository)
    }

    @Test
    fun `networkModule provides APIClient`() {
        // When
        val apiClient: APIClient by inject()

        // Then
        assertNotNull(apiClient, "APIClient should be injected")
    }

    @Test
    fun `networkModule provides RemoteMenuRepository`() {
        // When
        val remoteMenuRepository: RemoteMenuRepository by inject()

        // Then
        assertNotNull(remoteMenuRepository, "RemoteMenuRepository should be injected")
    }

    @Test
    fun `repositoryModule provides DishRepository`() {
        // When
        val dishRepository: DishRepository by inject()

        // Then
        assertNotNull(dishRepository, "DishRepository should be injected")
    }

    @Test
    fun `repositoryModule provides CartRepository`() {
        // When
        val cartRepository: CartRepository by inject()

        // Then
        assertNotNull(cartRepository, "CartRepository should be injected")
    }

    @Test
    fun `repositoryModule provides MenuRepository`() {
        // When
        val menuRepository: MenuRepository by inject()

        // Then
        assertNotNull(menuRepository, "MenuRepository should be injected")
    }

    @Test
    fun `viewModelModule provides HomeViewModel`() {
        // When
        val homeViewModel: HomeViewModel by inject()

        // Then
        assertNotNull(homeViewModel, "HomeViewModel should be injected")
        assertNotNull(homeViewModel.dishRepository, "HomeViewModel should have DishRepository")
    }

    @Test
    fun `viewModelModule provides MenuViewModel`() {
        // When
        val menuViewModel: MenuViewModel by inject()

        // Then
        assertNotNull(menuViewModel, "MenuViewModel should be injected")
    }

    @Test
    fun `viewModelModule provides CartViewModel`() {
        // When
        val cartViewModel: CartViewModel by inject()

        // Then
        assertNotNull(cartViewModel, "CartViewModel should be injected")
        assertNotNull(cartViewModel.cartRepository, "CartViewModel should have CartRepository")
    }

    @Test
    fun `viewModelModule provides DishDetailsViewModel`() {
        // When
        val dishDetailsViewModel: DishDetailsViewModel by inject()

        // Then
        assertNotNull(dishDetailsViewModel, "DishDetailsViewModel should be injected")
        assertNotNull(dishDetailsViewModel.cartRepository, "DishDetailsViewModel should have CartRepository")
        assertNotNull(dishDetailsViewModel.dishRepository, "DishDetailsViewModel should have DishRepository")
    }

    @Test
    fun `viewModelModule provides LoginViewModel`() {
        // When
        val loginViewModel: LoginViewModel by inject()

        // Then
        assertNotNull(loginViewModel, "LoginViewModel should be injected")
    }

    @Test
    fun `singletons are actually singletons`() {
        // When
        val apiClient1: APIClient by inject()
        val apiClient2: APIClient by inject()

        val dishRepository1: DishRepository by inject()
        val dishRepository2: DishRepository by inject()

        val cartRepository1: CartRepository by inject()
        val cartRepository2: CartRepository by inject()

        // Then
        assertSame(apiClient1, apiClient2, "APIClient should be singleton")
        assertSame(dishRepository1, dishRepository2, "DishRepository should be singleton")
        assertSame(cartRepository1, cartRepository2, "CartRepository should be singleton")
    }

    @Test
    fun `viewModels are different instances`() {
        // When
        val homeViewModel1: HomeViewModel by inject()
        val homeViewModel2: HomeViewModel by inject()

        val loginViewModel1: LoginViewModel by inject()
        val loginViewModel2: LoginViewModel by inject()

        // Then
        assertNotSame(homeViewModel1, homeViewModel2, "HomeViewModel instances should be different")
        assertNotSame(loginViewModel1, loginViewModel2, "LoginViewModel instances should be different")
    }

    @Test
    fun `dependencies are injected correctly in ViewModels`() {
        // When
        val homeViewModel: HomeViewModel by inject()
        val cartViewModel: CartViewModel by inject()
        val dishDetailsViewModel: DishDetailsViewModel by inject()

        val dishRepository: DishRepository by inject()
        val cartRepository: CartRepository by inject()

        // Then
        assertSame(dishRepository, homeViewModel.dishRepository, 
            "HomeViewModel should get the same DishRepository instance")
        assertSame(cartRepository, cartViewModel.cartRepository,
            "CartViewModel should get the same CartRepository instance")
        assertSame(cartRepository, dishDetailsViewModel.cartRepository,
            "DishDetailsViewModel should get the same CartRepository instance")
        assertSame(dishRepository, dishDetailsViewModel.dishRepository,
            "DishDetailsViewModel should get the same DishRepository instance")
    }

    @Test
    fun `complex dependency chain works`() {
        // When
        val menuViewModel: MenuViewModel by inject()
        val remoteMenuRepository: RemoteMenuRepository by inject()
        val menuRepository: MenuRepository by inject()
        val apiClient: APIClient by inject()

        // Then
        assertNotNull(menuViewModel, "MenuViewModel should be created")
        assertNotNull(remoteMenuRepository, "RemoteMenuRepository should be created") 
        assertNotNull(menuRepository, "MenuRepository should be created")
        assertNotNull(apiClient, "APIClient should be created")
    }
}
