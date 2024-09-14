package com.example.challenge.models

data class User(
    val cpf: String,
    val senha: String? = null,
    val email: String? = null
)
