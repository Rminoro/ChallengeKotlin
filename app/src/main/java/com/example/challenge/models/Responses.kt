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

data class RegisterResponse(
    val success: Boolean, // Indica se o registro foi bem-sucedido
    val message: String   // Mensagem de sucesso ou erro
)