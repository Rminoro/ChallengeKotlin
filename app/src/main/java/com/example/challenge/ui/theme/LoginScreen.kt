package com.example.challenge.ui.theme

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.challenge.api.RetrofitInstance
import com.example.challenge.models.User
import com.example.challenge.models.LoginResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun LoginScreen(
    onForgotPasswordClick: () -> Unit = {},
    onRegisterClick: () -> Unit = {},
    onCrudClick: () -> Unit // Parâmetro para navegação para CRUD
) {
    var cpf by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var loginResult by remember { mutableStateOf<Result<LoginResponse>?>(null) }

    val context = LocalContext.current
    val sharedPreferences = context.getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
    val coroutineScope = rememberCoroutineScope() // Mover a definição para cá

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
                    val result: Result<LoginResponse> = try {
                        val response = withContext(Dispatchers.IO) {
                            RetrofitInstance.api.login(User(cpf, senha))
                        }
                        if (response.isSuccessful) {
                            Result.success(response.body()!!)
                        } else {
                            Result.failure(Exception(response.message()))
                        }
                    } catch (e: Exception) {
                        Result.failure(e)
                    }
                    loginResult = result
                    isLoading = false
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isLoading) {
                CircularProgressIndicator(modifier = Modifier.size(24.dp))
            } else {
                Text("Login")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        // Link "Esqueceu a senha?"
        Text(
            text = "Esqueceu a senha?",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable {
                    onForgotPasswordClick()
                }
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Link "Registre-se"
        Text(
            text = "Registre-se",
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier
                .clickable {
                    onRegisterClick()
                }
                .padding(8.dp)
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Novo botão "Acessar CRUD"
        Button(
            onClick = { onCrudClick() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Acessar CRUD")
        }
    }

    // Exibir Toast ou outro feedback para o usuário baseado no resultado
    LaunchedEffect(loginResult) {
        loginResult?.let {
            when {
                it.isSuccess -> {
                    val user = it.getOrNull()
                    Toast.makeText(context, "Login bem-sucedido: ${user?.user?.cpf}", Toast.LENGTH_LONG).show()

                    // Armazene o CPF no SharedPreferences
                    with(sharedPreferences.edit()) {
                        putString("user_cpf", user?.user?.cpf) // Armazena o CPF
                        apply() // Salva as alterações
                    }

                    // Redireciona para o link após login bem-sucedido
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://claud-ia-website.vercel.app/"))
                    context.startActivity(intent)

                    // Navegar para a tela CRUD
                    onCrudClick()
                }
                it.isFailure -> {
                    val error = it.exceptionOrNull()
                    Toast.makeText(context, "Erro no login: ${error?.message}", Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewLoginScreen() {
    MaterialTheme {
        LoginScreen(
            onCrudClick = { /* Navegar para a tela CRUD */ }
        )
    }
}
