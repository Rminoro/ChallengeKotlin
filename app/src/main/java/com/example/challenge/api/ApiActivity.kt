package com.example.challenge.api

import com.example.challenge.models.User
import com.example.challenge.models.LoginResponse
import com.example.challenge.models.RecoveryResponse
import com.example.challenge.models.RegisterResponse
import com.example.challenge.models.ResetPasswordResponse
import com.example.challenge.models.RecoveryUser
import com.example.challenge.models.RegisterUser
import com.example.challenge.models.ResetPasswordUser

import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/login")
    suspend fun login(@Body user: User): retrofit2.Response<LoginResponse>

    @POST("/recuperar_senha")
    suspend fun recuperarSenha(@Body user: RecoveryUser): retrofit2.Response<RecoveryResponse>

    @POST("/register")
    suspend fun register(@Body user: RegisterUser): retrofit2.Response<RegisterResponse>

    @POST("/redefinir_senha")
    suspend fun resetPassword(@Body user: ResetPasswordUser): retrofit2.Response<ResetPasswordResponse>
}

