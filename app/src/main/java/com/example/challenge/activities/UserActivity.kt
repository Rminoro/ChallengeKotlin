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

class UserActivity : AppCompatActivity() {

    private lateinit var cpfEditText: EditText
    private lateinit var senhaEditText: EditText
    private lateinit var createButton: Button
    private lateinit var getButton: Button
    private lateinit var updateButton: Button
    private lateinit var deleteButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user)

        cpfEditText = findViewById(R.id.cpfEditText)
        senhaEditText = findViewById(R.id.senhaEditText)
        createButton = findViewById(R.id.createButton)
        getButton = findViewById(R.id.getButton)
        updateButton = findViewById(R.id.updateButton)
        deleteButton = findViewById(R.id.deleteButton)

        createButton.setOnClickListener { createUser() }
        getButton.setOnClickListener { getUser() }
        updateButton.setOnClickListener { updateUser() }
        deleteButton.setOnClickListener { deleteUser() }
    }

    private fun createUser() {
        val cpf = cpfEditText.text.toString()
        val senha = senhaEditText.text.toString()
        val user = User(cpf, senha)

        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitInstance.api.createUser(user)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Toast.makeText(this@UserActivity, "Usuário criado com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@UserActivity, "Erro ao criar usuário: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun getUser() {
        val cpf = cpfEditText.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitInstance.api.getUser(cpf)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val user = response.body()
                    if (user != null) {
                        Toast.makeText(this@UserActivity, "Usuário encontrado: $user", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this@UserActivity, "Erro ao buscar usuário: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun updateUser() {
        val cpf = cpfEditText.text.toString()
        val senha = senhaEditText.text.toString()
        val user = User(cpf, senha)

        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitInstance.api.updateUser(cpf, user)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Toast.makeText(this@UserActivity, "Usuário atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@UserActivity, "Erro ao atualizar usuário: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun deleteUser() {
        val cpf = cpfEditText.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            val response = RetrofitInstance.api.deleteUser(cpf)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    Toast.makeText(this@UserActivity, "Usuário excluído com sucesso!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this@UserActivity, "Erro ao excluir usuário: ${response.message()}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
