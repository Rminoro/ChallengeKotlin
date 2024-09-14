package com.example.challenge.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
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
        setContentView(R.layout.activity_login)

        cpfEditText = findViewById(R.id.cpfEditText)
        senhaEditText = findViewById(R.id.senhaEditText)
        loginButton = findViewById(R.id.loginButton)

        loginButton.setOnClickListener {
            val cpf = cpfEditText.text.toString()
            val senha = senhaEditText.text.toString()

            val user = User(cpf, senha)

            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitInstance.api.login(user)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        Toast.makeText(this@LoginActivity, "Login bem-sucedido!", Toast.LENGTH_SHORT).show()
                        // Navegue para a próxima atividade
                    } else {
                        Toast.makeText(this@LoginActivity, "Erro ao fazer login: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
