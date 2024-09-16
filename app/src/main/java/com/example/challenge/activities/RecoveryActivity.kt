// Em com.example.challenge.activities.RecoveryActivity.kt
package com.example.challenge.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.challenge.R
import com.example.challenge.api.RetrofitInstance
import com.example.challenge.models.RecoveryUser // Corrija a importação
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecoveryActivity : AppCompatActivity() {

    private lateinit var cpfEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var recoveryButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recovery)

        cpfEditText = findViewById(R.id.cpfEditText)
        emailEditText = findViewById(R.id.emailEditText)
        recoveryButton = findViewById(R.id.recoveryButton)

        recoveryButton.setOnClickListener {
            val cpf = cpfEditText.text.toString()
            val email = emailEditText.text.toString()

            val recoveryUser = RecoveryUser(cpf, email) // Use o tipo correto

            CoroutineScope(Dispatchers.IO).launch {
                val response = RetrofitInstance.api.recuperarSenha(recoveryUser) // Use o tipo correto
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful && response.body()?.success == true) {
                        Toast.makeText(this@RecoveryActivity, "E-mail de recuperação enviado!", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@RecoveryActivity, "Erro ao recuperar senha: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}
