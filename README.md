# Desafio Kotlin - Grupo [NTJTech]
# Integrantes 
- 98974 - Ana Júlia Almeida Silva Neves
- 552410 - NICOLY OLIVEIRA SANTOS
- 99988 - Rafael Minoro Itokazo
- 551831 - Vitor da Silva Pereira
  
## Descrição do Projeto

O projeto consiste em um aplicativo com funções de LOGIN, SENHA, RECUPERAÇÃO DE SENHA(VIA TOKEN) e um CRUD de usuários onde os dados ficam alojados no Firebase, após executar o login a pessoa é redirecionada para um site onde é apresentado toda a idéia, execução e desenvolvimento do projeto.
O CRUD foi desenvolvido para adicionar, excluir, atualizar ou ver os usuários e/ou senhas.
A aplicação utiliza a biblioteca [Jetpack Compose](https://developer.android.com/jetpack/compose) para a construção da interface de usuário, oferecendo responsividade. Além disso, o projeto integra a API do [Retrofit] para a comunicação com um backend, possibilitando operações de login e acesso a funcionalidades adicionais.

## Tecnologias Utilizadas
- **Kotlin**: Linguagem de programação utilizada para o desenvolvimento da aplicação.
- **Jetpack Compose**: Biblioteca para construção de interfaces de usuário nativas.
- **Retrofit**: Ferramenta para facilitar a comunicação com APIs RESTful.
- **Coroutines**: Para gerenciamento assíncrono e execução de tarefas em segundo plano.
- **SharedPreferences**: Utilizado no lugar do assyncStorage para melhor experiência.

## Aplicação
Para a comunicação entre o Aplicativo e os dados do Firebase utilizamos um flask (https://github.com/Rminoro/pythonLoginChallenge) para mediar e validar os dados inseridos. Para executá-lo basta clonar o repositório, executar o comando "python app.py" no terminal e após isso utilizar o Aplicativo Kotlin.

## Estrutura de pastas
![pastas1](https://github.com/user-attachments/assets/19f96d8b-973a-4607-b363-85c79a3bea45)
![pastas2](https://github.com/user-attachments/assets/8114a687-6993-42e7-82b3-3bb7b03159b6)
