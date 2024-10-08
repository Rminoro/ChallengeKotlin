package com.example.challenge.activities

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.challenge.api.RetrofitInstance
import com.example.challenge.models.RegisterResponse
import com.example.challenge.models.RegisterUser

@Composable
fun RegisterScreen(onRegisterSuccess: () -> Unit = {}) {
    var cpf by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var registerResult by remember { mutableStateOf<Result<RegisterResponse>?>(null) }

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
            value = senha,
            onValueChange = { senha = it },
            label = { Text("Senha") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                isLoading = true
                coroutineScope.launch {
                    val result: Result<RegisterResponse> = try {
                        val response = withContext(Dispatchers.IO) {
                            RetrofitInstance.api.register(RegisterUser(cpf, senha, email))
                        }
                        if (response.isSuccessful) {
                            Result.success(response.body()!!)
                        } else {
                            Result.failure(Exception(response.message()))
                        }
                    } catch (e: Exception) {
                        Result.failure(e)
                    }
                    registerResult = result
                    isLoading = false
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text("Registrar")
            }
        }

        LaunchedEffect(registerResult) {
            registerResult?.let {
                when {
                    it.isSuccess -> {
                        val message = it.getOrNull()?.message ?: "Usuário registrado com sucesso!"
                        Toast.makeText(context, message, Toast.LENGTH_LONG).show()
                        onRegisterSuccess()
                    }
                    it.isFailure -> {
                        val error = it.exceptionOrNull()?.message ?: "Erro no registro"
                        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}
