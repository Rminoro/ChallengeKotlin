package com.example.challenge.ui.theme

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.example.challenge.api.RetrofitInstance
import com.example.challenge.models.RecoveryResponse
import com.example.challenge.models.RecoveryUser

@Composable
fun ForgotPasswordScreen(onBackToLoginClick: () -> Unit = {}, navController: NavController) {
    var cpf by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var recoveryResult by remember { mutableStateOf<Result<RecoveryResponse>?>(null) }

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
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                isLoading = true
                coroutineScope.launch {
                    val result: Result<RecoveryResponse> = try {
                        val response = withContext(Dispatchers.IO) {
                            val recoveryUser = RecoveryUser(cpf, email)
                            RetrofitInstance.api.recuperarSenha(recoveryUser)
                        }
                        if (response.isSuccessful) {
                            Result.success(response.body()!!)
                        } else {
                            Result.failure(Exception(response.errorBody()?.string() ?: response.message()))
                        }
                    } catch (e: Exception) {
                        Result.failure(e)
                    }
                    recoveryResult = result
                    isLoading = false
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text("Recuperar Senha")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Voltar ao login",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.clickable { onBackToLoginClick() }
        )

        LaunchedEffect(recoveryResult) {
            recoveryResult?.let {
                when {
                    it.isSuccess -> {
                        val token = it.getOrNull()?.token ?: ""
                        // Navegar para a tela de redefinição de senha com o token como argumento
                        navController.navigate("reset_password/$token")
                    }
                    it.isFailure -> {
                        val error = it.exceptionOrNull()?.message ?: "Erro ao recuperar senha."
                        Toast.makeText(context, error, Toast.LENGTH_LONG).show()
                    }
                }
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun PreviewForgotPasswordScreen() {
    val navController = rememberNavController()
    MaterialTheme {
        ForgotPasswordScreen(navController = navController)
    }
}
