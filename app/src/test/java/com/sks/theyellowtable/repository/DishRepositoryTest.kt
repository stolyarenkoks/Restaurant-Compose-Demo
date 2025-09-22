package com.sks.theyellowtable.repository

import com.sks.theyellowtable.R
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class DishRepositoryTest {

    private lateinit var repository: DishRepositoryImpl

    @Before
    fun setup() {
        repository = DishRepositoryImpl()
    }

    @Test
    fun `dishes list is not empty`() {
        // Then
        assertTrue("Dishes list should not be empty", repository.dishes.isNotEmpty())
    }

    @Test
    fun `dishes list contains expected number of items`() {
        // Then
        assertEquals("Should have 6 dishes", 6, repository.dishes.size)
    }

    @Test
    fun `dishes have unique ids`() {
        // Given
        val dishes = repository.dishes
        
        // When
        val ids = dishes.map { it.id }

        // Then
        assertEquals("All dish IDs should be unique", ids.size, ids.toSet().size)
    }

    @Test
    fun `dishes have valid prices`() {
        // Given
        val dishes = repository.dishes

        // Then
        dishes.forEach { dish ->
            assertTrue("Dish ${dish.id} should have positive price", dish.price > 0.0)
        }
    }

    @Test
    fun `getDish returns correct dish by id`() = runTest {
        // Given
        val expectedDishId = 1

        // When
        val dish = repository.getDish(expectedDishId)

        // Then
        assertNotNull("Should return a dish", dish)
        assertEquals("Should return dish with correct ID", expectedDishId, dish?.id)
        assertEquals("Should be Greek Salad", R.string.greek_salad, dish?.nameResourceId)
    }

    @Test
    fun `getDish returns null for non-existing id`() = runTest {
        // Given
        val nonExistingId = 999

        // When
        val dish = repository.getDish(nonExistingId)

        // Then
        assertNull("Should return null for non-existing ID", dish)
    }

    @Test
    fun `getDish returns null for negative id`() = runTest {
        // Given
        val negativeId = -1

        // When
        val dish = repository.getDish(negativeId)

        // Then
        assertNull("Should return null for negative ID", dish)
    }

    @Test
    fun `all dishes have valid resource IDs`() {
        // Given
        val dishes = repository.dishes

        // Then
        dishes.forEach { dish ->
            assertTrue(
                "Dish ${dish.id} should have valid name resource ID",
                dish.nameResourceId > 0
            )
            assertTrue(
                "Dish ${dish.id} should have valid description resource ID",
                dish.descriptionResourceId > 0
            )
            assertTrue(
                "Dish ${dish.id} should have valid image resource ID",
                dish.imageResource > 0
            )
        }
    }

    @Test
    fun `dishes are ordered by id`() {
        // Given
        val dishes = repository.dishes
        
        // When
        val ids = dishes.map { it.id }

        // Then
        assertEquals(
            "Dishes should be ordered by ID",
            ids.sorted(),
            ids
        )
    }
}
