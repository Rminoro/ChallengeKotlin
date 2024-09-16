package com.example.challenge.ui.theme

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.challenge.api.RetrofitInstance
import com.example.challenge.models.ResetPasswordResponse
import com.example.challenge.models.ResetPasswordUser

@Composable
fun ResetPasswordScreen(
    onPasswordResetSuccess: () -> Unit = {},
    token: String? = null // Receba o token como um parâmetro opcional
) {
    var cpf by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var novaSenha by remember { mutableStateOf("") }
    var tokenInput by remember { mutableStateOf(token ?: "") } // Inicialize com o token, se disponível
    var isLoading by remember { mutableStateOf(false) }
    var resetResult by remember { mutableStateOf<Result<ResetPasswordResponse>?>(null) }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = cpf,
            onValueChange = { cpf = it },
            label = { Text("CPF") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = tokenInput,
            onValueChange = { tokenInput = it },
            label = { Text("Token") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            value = novaSenha,
            onValueChange = { novaSenha = it },
            label = { Text("Nova Senha") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                isLoading = true
                coroutineScope.launch {
                    val result: Result<ResetPasswordResponse> = try {
                        val response = withContext(Dispatchers.IO) {
                            RetrofitInstance.api.resetPassword(
                                ResetPasswordUser(
                                    cpf,
                                    email,
                                    tokenInput, // Use o valor do token inserido
                                    novaSenha
                                )
                            )
                        }
                        if (response.isSuccessful) {
                            Result.success(response.body()!!)
                        } else {
                            Result.failure(Exception(response.errorBody()?.string() ?: response.message()))
                        }
                    } catch (e: Exception) {
                        Result.failure(e)
                    }
                    resetResult = result
                    isLoading = false
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text("Redefinir Senha")
            }
        }

        LaunchedEffect(resetResult) {
            resetResult?.let {
                when {
                    it.isSuccess -> {
                        val message = it.getOrNull()?.message ?: "Senha redefinida com sucesso!"
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                        onPasswordResetSuccess()
                    }
                    it.isFailure -> {
                        val error = it.exceptionOrNull()?.message ?: "Erro ao redefinir senha."
                        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
