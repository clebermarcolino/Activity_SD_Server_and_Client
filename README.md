# Bate-papo TCP Multiusuário 📬

Bem-vindo ao Bate-papo TCP Multiusuário, um sistema de chat em linha de comando desenvolvido em Java\! Este projeto permite que vários usuários se conectem a um servidor via sockets TCP, troquem mensagens em tempo real e recebam mensagens do administrador do servidor. Com suporte a múltiplos usuários (mais de 2), este é um exemplo robusto de comunicação em rede com concorrência.

-----

## ℹ️ Sobre o Projeto

Este projeto foi criado como parte da Prática 1 da disciplina de Sistemas Distribuídos ministrada pelo professor Marcus Carvalho. Ele foi estendido para implementar um bate-papo multiusuário, onde:

  * **Clientes** se conectam ao servidor, escolhem um nome de usuário e enviam mensagens.
  * O **servidor** retransmite (broadcast) as mensagens para todos os outros clientes.
  * O **administrador do servidor** pode enviar mensagens globais.
  * **Notificações** de entrada e saída de usuários são exibidas para todos.

O sistema é ideal para demonstrar conceitos de redes, sockets, threads e concorrência em Java.

-----

## 🚀 Tecnologias Utilizadas

  * **Java SE**: Linguagem principal para desenvolvimento do servidor e clientes, utilizando a API padrão do Java.
  * **Sockets TCP**: Comunicação confiável e bidirecional via `ServerSocket` e `Socket` (`java.net`).
  * **Threads**: Concorrência para gerenciar múltiplos clientes no servidor e leitura/escrita assíncronas nos clientes (`java.lang.Thread`).
  * **ExecutorService**: Gerenciamento eficiente de pool de threads no servidor (`java.util.concurrent`).
  * **Java I/O**: Leitura e escrita de mensagens em formato UTF-8 com `DataInputStream` e `DataOutputStream` (`java.io`).

-----

## 🌟 Funcionalidades

  * **Chat multiusuário**: Suporta mais de 2 usuários conectados simultaneamente.
  * **Mensagens bidirecionais**: Clientes e servidor podem enviar mensagens, exibidas como `[nome]: mensagem` ou `[Servidor]: mensagem`.
  * **Nomes de usuário**: Cada cliente define um nome único ao conectar.
  * **Notificações**: Exibe mensagens quando usuários entram ou saem do chat.
  * **Desconexão segura**: O comando `sair` finaliza a conexão do cliente, notificando os outros.
  * **Timeout**: Clientes possuem timeout de 30 segundos para evitar bloqueios.
  * **Concorrência**: Servidor usa pool de threads, e clientes usam threads separadas para envio e recebimento.

-----

## 🛠️ Como Instalar e Executar

### Pré-requisitos

  * Java Development Kit (JDK) versão 8 ou superior.
  * Terminal ou IDE (ex.: IntelliJ, Eclipse, VS Code) com suporte a múltiplas instâncias.
  * Sistema operacional: Windows, Linux ou macOS.

### Passos

1.  **Clonar o repositório**:

    ```bash
    git clone https://github.com/seu-usuario/ChatTCPMultiUser.git
    cd ChatTCPMultiUser
    ```

2.  **Compilar os arquivos**:

    ```bash
    javac ThreadPoolTCPServer.java ClientHandler.java SimpleTCPClient.java
    ```

3.  **Iniciar o servidor**:
    Em um terminal, execute:

    ```bash
    java ThreadPoolTCPServer
    ```

    Saída esperada:

    ```
    Servidor iniciado em: 127.0.0.1:6666
    Aguardando conexão...
    ```

4.  **Iniciar os clientes**:
    Abra múltiplos terminais (3 ou mais para testar). Em cada um, execute:

    ```bash
    java SimpleTCPClient
    ```

      * Digite um nome de usuário (ex.: Alice, Bob, Carol).
      * Envie mensagens no prompt `Mensagem:`.
      * Use `sair` para desconectar.

5.  **Enviar mensagens do servidor**:
    No terminal do servidor, digite mensagens no prompt `Mensagem do servidor:` para enviá-las a todos os clientes.

-----

## 🧪 Testando o Chat Multiusuário

Para verificar o suporte a múltiplos usuários:

1.  Inicie o servidor.
2.  Conecte 3 ou mais clientes em terminais separados.
3.  Envie mensagens entre clientes e do servidor.
4.  Verifique se:
      * Mensagens são recebidas por todos os outros clientes (exceto o remetente).
      * Mensagens do servidor aparecem com `[Servidor]:`.
      * Notificações de entrada/saída (ex.: `Alice entrou no chat!`) são exibidas.
      * Desconecte um cliente com `sair` e confirme a notificação nos outros.

### Exemplo de Uso

**Servidor:**

```
Servidor iniciado em: 127.0.0.1:6666
Aguardando conexão...
Conexão estabelecida com: /127.0.0.1:50001
Usuário conectado: Alice (/127.0.0.1:50001)
Broadcast: Alice entrou no chat!
Conexão estabelecida com: /127.0.0.1:50002
Usuário conectado: Bob (/127.0.0.1:50002)
Broadcast: Bob entrou no chat!
Mensagem do servidor: Bem-vindos ao chat!
Broadcast do servidor: [Servidor]: Bem-vindos ao chat!
Broadcast: [Alice]: Olá, todos!
Broadcast: [Bob]: Oi, Alice!
```

**Cliente Alice:**

```
Digite seu nome de usuário: Alice
[C1] Conectado como: Alice em 127.0.0.1:6666
Bob entrou no chat!
[Servidor]: Bem-vindos ao chat!
Mensagem: Olá, todos!
[Bob]: Oi, Alice!
```

**Cliente Bob:**

```
Digite seu nome de usuário: Bob
[C1] Conectado como: Bob em 127.0.0.1:6666
Alice entrou no chat!
[Servidor]: Bem-vindos ao chat!
[Alice]: Olá, todos!
Mensagem: Oi, Alice!
```

-----

## 🔍 Modificações Realizadas

Este projeto foi baseado no código da Prática 1, com as seguintes melhorias:

  * **Suporte multiusuário**: Adicionada uma lista estática no `ClientHandler` para rastrear clientes e um método `broadcast` para enviar mensagens.
  * **Mensagens do servidor**: Implementada uma thread no `ThreadPoolTCPServer` para permitir que o administrador envie mensagens.
  * **Cliente assíncrono**: O `SimpleTCPClient` usa duas threads para leitura e escrita simultâneas.
  * **Robustez**:
      * Timeout de 30 segundos no cliente.
      * Sincronização na lista de clientes para evitar concorrência.
      * Tratamento de erros para desconexões abruptas.
  * **Formato de mensagens**:
      * Clientes: `[nome]: mensagem`
      * Servidor: `[Servidor]: mensagem`
      * Notificações: `nome entrou no chat!` ou `nome saiu!`

-----

## 📂 Estrutura do Repositório

```
├── ThreadPoolTCPServer.java
├── ClientHandler.java
├── SimpleTCPClient.java
└── README.md
```
