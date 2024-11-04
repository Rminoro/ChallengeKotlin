package com.example.challenge.ui.theme

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import com.example.challenge.api.RetrofitInstance
import com.example.challenge.models.User
import com.example.challenge.models.RegisterUser
import com.example.challenge.models.ResetPasswordUser
import com.example.challenge.models.RecoveryUser
import androidx.compose.ui.platform.LocalContext

@Composable
fun CrudScreen() {
    var cpf by remember { mutableStateOf("") }
    var senha by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var token by remember { mutableStateOf("") }
    var novaSenha by remember { mutableStateOf("") }

    val context = LocalContext.current // Obtém o contexto local

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Tela de CRUD", style = MaterialTheme.typography.titleLarge)

        OutlinedTextField(
            value = cpf,
            onValueChange = { cpf = it },
            label = { Text("CPF") }
        )

        OutlinedTextField(
            value = senha,
            onValueChange = { senha = it },
            label = { Text("Senha") }
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") }
        )

        OutlinedTextField(
            value = token,
            onValueChange = { token = it },
            label = { Text("Token") }
        )

        OutlinedTextField(
            value = novaSenha,
            onValueChange = { novaSenha = it },
            label = { Text("Nova Senha") }
        )

        Button(onClick = { registerUser(cpf, senha, email, context) }) {
            Text("Registrar Usuário")
        }

        Button(onClick = { loginUser(cpf, senha, context) }) {
            Text("Login")
        }

        Button(onClick = { recoverPassword(cpf, email, context) }) {
            Text("Recuperar Senha")
        }

        Button(onClick = { resetPassword(cpf, email, token, novaSenha, context) }) {
            Text("Redefinir Senha")
        }
    }
}

private fun registerUser(cpf: String, senha: String, email: String, context: android.content.Context) {
    CoroutineScope(Dispatchers.IO).launch {
        val response = RetrofitInstance.api.register(RegisterUser(cpf, senha, email))
        handleResponse(response, "Usuário registrado com sucesso!", "Erro ao registrar usuário.", context)
    }
}

private fun loginUser(cpf: String, senha: String, context: android.content.Context) {
    CoroutineScope(Dispatchers.IO).launch {
        val response = RetrofitInstance.api.login(User(cpf, senha))
        handleResponse(response, "Login realizado com sucesso!", "Erro ao fazer login.", context)
    }
}

private fun recoverPassword(cpf: String, email: String, context: android.content.Context) {
    CoroutineScope(Dispatchers.IO).launch {
        val response = RetrofitInstance.api.recuperarSenha(RecoveryUser(cpf, email))
        handleResponse(response, "Token de recuperação enviado!", "Erro ao recuperar senha.", context)
    }
}

private fun resetPassword(cpf: String, email: String, token: String, novaSenha: String, context: android.content.Context) {
    CoroutineScope(Dispatchers.IO).launch {
        val response = RetrofitInstance.api.resetPassword(ResetPasswordUser(cpf, email, token, novaSenha))
        handleResponse(response, "Senha redefinida com sucesso!", "Erro ao redefinir senha.", context)
    }
}

private suspend fun handleResponse(response: Response<*>, successMessage: String, errorMessage: String, context: android.content.Context) {
    withContext(Dispatchers.Main) {
        if (response.isSuccessful) {
            Toast.makeText(context, successMessage, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(context, "$errorMessage: ${response.message()}", Toast.LENGTH_SHORT).show()
        }
    }
}
