package com.sks.theyellowtable.screens.cart

import com.sks.theyellowtable.R
import com.sks.theyellowtable.models.CartItem
import com.sks.theyellowtable.models.Dish
import com.sks.theyellowtable.models.mock
import com.sks.theyellowtable.repository.CartRepository
import io.mockk.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import kotlinx.coroutines.Dispatchers
import org.junit.Rule
import com.sks.theyellowtable.utils.TestDispatcherRule

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelTest {

    @get:Rule
    val mainDispatcherRule = TestDispatcherRule()

    private lateinit var mockRepository: CartRepository
    private lateinit var viewModel: CartViewModel

    private val mockDish1 = Dish.mock()
    private val mockDish2 = Dish.mock(id = 2, nameResourceId = R.string.pasta, price = 18.99)
    
    private val mockCartFlow = MutableStateFlow<List<CartItem>>(emptyList())

    @Before
    fun setup() {
        mockRepository = mockk(relaxed = true)
        
        every { mockRepository.cartItems } returns mockCartFlow
        every { mockRepository.totalPrice } returns 0.0
        
        viewModel = CartViewModel(mockRepository)
    }

    @Test
    fun `cartItems flow is observed correctly`() = runTest {
        // Given
        val cartItems = listOf(CartItem(mockDish1, 2))
        
        // When
        mockCartFlow.value = cartItems

        // Then
        assertEquals(
            "Should observe cart items",
            cartItems,
            viewModel.cartItems.value
        )
    }

    @Test
    fun `totalPrice returns repository totalPrice`() {
        // Given
        val expectedPrice = 25.98
        every { mockRepository.totalPrice } returns expectedPrice

        // When
        val actualPrice = viewModel.totalPrice

        // Then
        assertEquals(
            "Should return repository total price",
            expectedPrice,
            actualPrice,
            0.01
        )
    }

    @Test
    fun `addToCart calls repository addToCart`() = runTest {
        // Given
        val dish = mockDish1
        val quantity = 3
        coEvery { mockRepository.addToCart(any(), any()) } just runs

        // When
        viewModel.addToCart(dish, quantity)

        // Then
        coVerify(exactly = 1) { mockRepository.addToCart(dish, quantity) }
    }

    @Test
    fun `removeItem calls repository removeFromCart`() = runTest {
        // Given
        val dishId = 1
        coEvery { mockRepository.removeFromCart(any()) } just runs

        // When
        viewModel.removeItem(dishId)

        // Then
        coVerify(exactly = 1) { mockRepository.removeFromCart(dishId) }
    }

    @Test
    fun `updateQuantity calls repository updateQuantity`() = runTest {
        // Given
        val dishId = 1
        val newQuantity = 5
        coEvery { mockRepository.updateQuantity(any(), any()) } just runs

        // When
        viewModel.updateQuantity(dishId, newQuantity)

        // Then
        coVerify(exactly = 1) { mockRepository.updateQuantity(dishId, newQuantity) }
    }

    @Test
    fun `clearCart calls repository clearCart`() = runTest {
        // Given
        coEvery { mockRepository.clearCart() } just runs

        // When
        viewModel.clearCart()

        // Then
        coVerify(exactly = 1) { mockRepository.clearCart() }
    }

    @Test
    fun `multiple operations work correctly`() = runTest {
        // Given
        coEvery { mockRepository.addToCart(any(), any()) } just runs
        coEvery { mockRepository.updateQuantity(any(), any()) } just runs
        coEvery { mockRepository.removeFromCart(any()) } just runs

        // When
        viewModel.addToCart(mockDish1, 2)
        viewModel.addToCart(mockDish2, 1)
        viewModel.updateQuantity(1, 3)
        viewModel.removeItem(2)

        // Then
        coVerify(exactly = 1) { mockRepository.addToCart(mockDish1, 2) }
        coVerify(exactly = 1) { mockRepository.addToCart(mockDish2, 1) }
        coVerify(exactly = 1) { mockRepository.updateQuantity(1, 3) }
        coVerify(exactly = 1) { mockRepository.removeFromCart(2) }
    }

    @Test
    fun `cartItems state updates are reflected in ViewModel`() = runTest {
        // Given
        val initialItems = emptyList<CartItem>()
        val updatedItems = listOf(
            CartItem(mockDish1, 2),
            CartItem(mockDish2, 1)
        )

        // When
        mockCartFlow.value = initialItems
        // Then
        assertEquals("Should start with empty cart", initialItems, viewModel.cartItems.value)

        // When
        mockCartFlow.value = updatedItems
        // Then
        assertEquals("Should update cart items", updatedItems, viewModel.cartItems.value)
        assertEquals("Should have 2 items", 2, viewModel.cartItems.value.size)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class CartViewModelIntegrationTest {

    @get:Rule
    val mainDispatcherRule = TestDispatcherRule()

    private lateinit var viewModel: CartViewModel
    private val mockDish = Dish.mock()

    @Before
    fun setup() {
        // Given
        val repository = com.sks.theyellowtable.repository.CartRepositoryImpl()
        viewModel = CartViewModel(repository)
        
        runTest {
            viewModel.clearCart()
        }
    }

    @Test
    fun `integration test with real repository`() = runTest {
        // Then
        assertTrue("Cart should be empty initially", viewModel.cartItems.value.isEmpty())
        assertEquals("Total price should be 0", 0.0, viewModel.totalPrice, 0.01)

        // When
        viewModel.addToCart(mockDish, 2)
        
        // Then - changes should be synchronous with MutableStateFlow.update
    }
}
