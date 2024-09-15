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

class RegisterActivity : AppCompatActivity() {

    private lateinit var cpfEditText: EditText
    private lateinit var senhaEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var registerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // Inicializando as views
        cpfEditText = findViewById(R.id.cpfEditText)
        senhaEditText = findViewById(R.id.senhaEditText)
        emailEditText = findViewById(R.id.emailEditText)
        registerButton = findViewById(R.id.registerButton)

        registerButton.setOnClickListener {
            val cpf = cpfEditText.text.toString()
            val senha = senhaEditText.text.toString()
            val email = emailEditText.text.toString()

            val user = User(cpf, senha, email)

            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitInstance.api.register(user)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        Toast.makeText(this@RegisterActivity, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this@RegisterActivity, "Erro ao cadastrar: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
