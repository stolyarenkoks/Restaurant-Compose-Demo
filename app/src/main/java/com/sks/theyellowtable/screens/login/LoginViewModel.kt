package com.sks.theyellowtable.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    var showHint by mutableStateOf(false)

    fun validateCredentials(
        username: String,
        password: String
    ): Boolean {
        val isValid = password == "1234" && !username.isEmpty()
        showHint = !isValid
        return isValid
    }
}