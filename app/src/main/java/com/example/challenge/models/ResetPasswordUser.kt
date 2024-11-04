// ResetPasswordUser.kt
package com.example.challenge.models

data class ResetPasswordUser(
    val cpf: String,
    val email: String,
    val token: String,
    val novaSenha: String
)


