package com.example.challenge.activities

import com.example.challenge.R
import com.example.challenge.api.RetrofitInstance

class egisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

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
