package com.example.challenge.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.challenge.R
import com.example.challenge.api.RetrofitInstance
import com.example.challenge.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var cpfEditText: EditText
    private lateinit var senhaEditText: EditText
    private lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)  // Verifique se este é o layout correto

        cpfEditText = findViewById(R.id.cpfEditText)
        senhaEditText = findViewById(R.id.senhaEditText)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val cpf = cpfEditText.text.toString()
            val senha = senhaEditText.text.toString()

            val user = User(cpf, senha)

            Log.d("LoginActivity", "Attempting to login with CPF: $cpf")

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = RetrofitInstance.api.login(user)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful && response.body()?.success == true) {
                            Toast.makeText(this@LoginActivity, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@LoginActivity, "Erro ao fazer login: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                        }
                        Log.d("LoginActivity", "Response: ${response.body()}")
                    }
                } catch (e: Exception) {
                    Log.e("LoginActivity", "Error de conexão: ${e.message}", e)
                }
            }
        }

    }
}
