package com.example.challenge.activities

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.challenge.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import com.example.challenge.api.RetrofitInstance
import com.example.challenge.models.User
import com.example.challenge.models.RegisterUser
import com.example.challenge.models.RecoveryUser
import com.example.challenge.models.ResetPasswordUser


class CrudActivity : AppCompatActivity() {

    private lateinit var cpfEditText: EditText
    private lateinit var senhaEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var tokenEditText: EditText
    private lateinit var novaSenhaEditText: EditText
    private lateinit var loginButton: Button
    private lateinit var registerButton: Button
    private lateinit var recoveryButton: Button
    private lateinit var resetButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.crud_activity)

        cpfEditText = findViewById(R.id.cpfEditText)
        senhaEditText = findViewById(R.id.senhaEditText)
        emailEditText = findViewById(R.id.emailEditText)
        tokenEditText = findViewById(R.id.tokenEditText) // Certifique-se que isso esteja aqui
        novaSenhaEditText = findViewById(R.id.novaSenhaEditText) // Certifique-se que isso esteja aqui
        loginButton = findViewById(R.id.loginButton)
        registerButton = findViewById(R.id.registerButton)
        recoveryButton = findViewById(R.id.recoveryButton)
        resetButton = findViewById(R.id.resetButton) // Certifique-se que isso esteja aqui

        // Aqui você pode definir um clique no botão resetButton
        resetButton.setOnClickListener {
            resetPassword()
        }
    }

    private fun loginUser() {
        val cpf = cpfEditText.text.toString()
        val senha = senhaEditText.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitInstance.api.login(User(cpf, senha))
            handleResponse(response, "Login realizado com sucesso!", "Erro ao fazer login.")
        }
    }

    private fun registerUser() {
        val cpf = cpfEditText.text.toString()
        val senha = senhaEditText.text.toString()
        val email = emailEditText.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitInstance.api.register(RegisterUser(cpf, senha, email))
            handleResponse(response, "Usuário registrado com sucesso!", "Erro ao registrar usuário.")
        }
    }

    private fun recoverPassword() {
        val cpf = cpfEditText.text.toString()
        val email = emailEditText.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitInstance.api.recuperarSenha(RecoveryUser(cpf, email))
            handleResponse(response, "Token de recuperação enviado!", "Erro ao recuperar senha.")
        }
    }

    private fun resetPassword() {
        val cpf = cpfEditText.text.toString()
        val email = emailEditText.text.toString()
        val token = tokenEditText.text.toString() // Aqui está a referência para tokenEditText
        val novaSenha = novaSenhaEditText.text.toString() // Aqui está a referência para novaSenhaEditText

        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitInstance.api.resetPassword(ResetPasswordUser(cpf, email, token, novaSenha))
            handleResponse(response, "Senha redefinida com sucesso!", "Erro ao redefinir senha.")
        }
    }



    private suspend fun handleResponse(response: Response<*>, successMessage: String, errorMessage: String) {
        withContext(Dispatchers.Main) {
            if (response.isSuccessful) {
                Toast.makeText(this@CrudActivity, successMessage, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this@CrudActivity, "$errorMessage: ${response.message()}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
