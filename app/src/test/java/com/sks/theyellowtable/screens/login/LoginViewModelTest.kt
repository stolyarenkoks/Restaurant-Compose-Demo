package com.sks.theyellowtable.screens.login

import org.junit.Test
import org.junit.Assert.*
import org.junit.Before

class LoginViewModelTest {

    private lateinit var viewModel: LoginViewModel

    @Before
    fun setup() {
        viewModel = LoginViewModel()
    }

    @Test
    fun `validateCredentials with empty username returns false`() {
        // Given
        val username = ""
        val password = "1234"

        // When
        val result = viewModel.validateCredentials(username, password)

        // Then
        assertFalse("Empty username should return false", result)
        assertTrue("Should show hint when validation fails", viewModel.showHint)
    }

    @Test
    fun `validateCredentials with wrong password returns false`() {
        // Given
        val username = "testuser"
        val password = "wrong"

        // When
        val result = viewModel.validateCredentials(username, password)

        // Then
        assertFalse("Wrong password should return false", result)
        assertTrue("Should show hint when validation fails", viewModel.showHint)
    }

    @Test
    fun `validateCredentials with correct credentials returns true`() {
        // Given
        val username = "testuser"
        val password = "1234"

        // When
        val result = viewModel.validateCredentials(username, password)

        // Then
        assertTrue("Valid credentials should return true", result)
        assertFalse("Should not show hint when validation succeeds", viewModel.showHint)
    }

    @Test
    fun `validateCredentials with empty username and wrong password returns false`() {
        // Given
        val username = ""
        val password = "wrong"

        // When
        val result = viewModel.validateCredentials(username, password)

        // Then
        assertFalse("Empty username and wrong password should return false", result)
        assertTrue("Should show hint when validation fails", viewModel.showHint)
    }

    @Test
    fun `showHint initially is false`() {
        // Then
        assertFalse("showHint should initially be false", viewModel.showHint)
    }

    @Test
    fun `multiple validation calls update showHint correctly`() {
        // Given
        viewModel.validateCredentials("", "1234")
        // Then
        assertTrue("Should show hint after first failed validation", viewModel.showHint)

        // When
        viewModel.validateCredentials("user", "1234")
        // Then
        assertFalse("Should hide hint after successful validation", viewModel.showHint)

        // When
        viewModel.validateCredentials("user", "wrong")
        // Then
        assertTrue("Should show hint again after failed validation", viewModel.showHint)
    }
}
