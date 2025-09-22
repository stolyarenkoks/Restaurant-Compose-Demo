package com.sks.theyellowtable.screens.home

import com.sks.theyellowtable.R
import com.sks.theyellowtable.models.Dish
import com.sks.theyellowtable.models.mock
import com.sks.theyellowtable.repository.DishRepository
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class HomeViewModelTest {

    private lateinit var mockRepository: DishRepository
    private lateinit var viewModel: HomeViewModel

    private val mockDishes = listOf(
        Dish.mock(),
        Dish.mock(2, R.string.bruschetta, R.string.bruschetta_description, 18.99),
        Dish.mock(3, R.string.pasta, R.string.pasta_description, 17.99)
    )

    @Before
    fun setup() {
        mockRepository = mockk()
        every { mockRepository.dishes } returns mockDishes
        viewModel = HomeViewModel(mockRepository)
    }

    @Test
    fun `dishes are loaded from repository`() {

        val mock = mockk<Dish>()
        // Then
        assertEquals("Should load dishes from repository", mockDishes, viewModel.dishes)
        assertEquals("Should have 3 dishes", 3, viewModel.dishes.size)
    }

    @Test
    fun `viewModel provides access to dish repository`() {
        // Then
        assertNotNull("Should have access to dish repository", viewModel.dishRepository)
        assertEquals("Should be the same repository instance", mockRepository, viewModel.dishRepository)
    }

    @Test
    fun `dishes contain expected data`() {
        // Given
        val dishes = viewModel.dishes

        // Then
        val firstDish = dishes[0]
        assertEquals("First dish should have ID 1", 1, firstDish.id)
        assertEquals("First dish should be Greek Salad", R.string.greek_salad, firstDish.nameResourceId)
        assertEquals("First dish price should be 12.99", 12.99, firstDish.price, 0.01)

        val secondDish = dishes[1]
        assertEquals("Second dish should have ID 2", 2, secondDish.id)
        assertEquals("Second dish should be Bruschetta", R.string.bruschetta, secondDish.nameResourceId)
        assertEquals("Second dish price should be 18.99", 18.99, secondDish.price, 0.01)
    }

    @Test
    fun `empty repository returns empty dishes list`() {
        // Given
        val emptyRepository = mockk<DishRepository>()
        every { emptyRepository.dishes } returns emptyList()
        
        // When
        val emptyViewModel = HomeViewModel(emptyRepository)

        // Then
        assertTrue("Should return empty list when repository is empty", emptyViewModel.dishes.isEmpty())
    }
}

class HomeViewModelIntegrationTest {

    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        // Given
        val realRepository = com.sks.theyellowtable.repository.DishRepositoryImpl()
        viewModel = HomeViewModel(realRepository)
    }

    @Test
    fun `integration test with real repository`() {
        // Then
        assertTrue("Should load dishes from real repository", viewModel.dishes.isNotEmpty())
        assertEquals("Should have 6 dishes from real repository", 6, viewModel.dishes.size)
        
        val firstDish = viewModel.dishes.first()
        assertEquals("First dish should have ID 1", 1, firstDish.id)
        assertTrue("First dish should have positive price", firstDish.price > 0.0)
    }
}
