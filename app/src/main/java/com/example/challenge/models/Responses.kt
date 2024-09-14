package com.example.challenge.models

data class LoginResponse(
    val success: Boolean,
    val user: User? = null,
    val message: String? = null
)

data class RecoveryResponse(
    val success: Boolean,
    val message: String? = null
)
