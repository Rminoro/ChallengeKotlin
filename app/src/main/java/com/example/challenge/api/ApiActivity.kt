package com.example.challenge.api

import com.example.challenge.models.User
import com.example.challenge.models.LoginResponse
import com.example.challenge.models.RecoveryResponse
import com.example.challenge.models.RegisterResponse
import com.example.challenge.models.ResetPasswordResponse
import com.example.challenge.models.RecoveryUser
import com.example.challenge.models.RegisterUser
import com.example.challenge.models.ResetPasswordUser
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("/login")
    suspend fun login(@Body user: User): Response<LoginResponse>

    @POST("/recuperar_senha")
    suspend fun recuperarSenha(@Body user: RecoveryUser): Response<RecoveryResponse>

    @POST("/register")
    suspend fun register(@Body user: RegisterUser): Response<RegisterResponse>

    @POST("/redefinir_senha")
    suspend fun resetPassword(@Body user: ResetPasswordUser): Response<ResetPasswordResponse>


    // Métodos CRUD para usuários
    @POST("/users") // Endpoint para criar um usuário
    suspend fun createUser(@Body user: User): Response<User>

    @GET("/users/{cpf}") // Endpoint para ler um usuário
    suspend fun getUser(@Path("cpf") cpf: String): Response<User>

    @PUT("/users/{cpf}") // Endpoint para atualizar um usuário
    suspend fun updateUser(@Path("cpf") cpf: String, @Body user: User): Response<User>

    @DELETE("/users/{cpf}") // Endpoint para excluir um usuário
    suspend fun deleteUser(@Path("cpf") cpf: String): Response<Unit>
}
