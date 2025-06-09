package com.sks.littlelemon.screens.login

import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    fun validateCredentials(
        username: String,
        password: String
    ): Boolean {
        // Any username Allowed
        return password == "1234"
    }
}