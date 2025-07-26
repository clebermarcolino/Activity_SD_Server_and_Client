# Sobre o projeto
Este projeto implementa um sistema de bate-papo em linha de comando utilizando sockets TCP, baseado no código da Prática 1 fornecido pelo professor. O sistema permite que múltiplos usuários (mais de 2) se conectem a um servidor, enviem mensagens entre si e recebam mensagens enviadas pelo administrador do servidor. As mensagens são retransmitidas (broadcast) para todos os outros clientes conectados, garantindo uma comunicação em grupo em tempo real.

## Tecnologias Utilizadas

Java: Linguagem de programação usada para desenvolver o servidor e os clientes. O projeto utiliza a API padrão do Java (Java SE) para implementar a lógica do bate-papo.
Sockets TCP: Usados para estabelecer comunicação confiável e bidirecional entre o servidor e os clientes, utilizando as classes ServerSocket e Socket do pacote java.net.
Threads: Empregadas para permitir concorrência, tanto no servidor (para gerenciar múltiplos clientes) quanto nos clientes (para envio e recebimento assíncrono de mensagens). A classe Thread é usada diretamente no cliente e no ClientHandler.
ExecutorService: Framework do pacote java.util.concurrent utilizado no servidor para gerenciar um pool de threads, permitindo lidar com múltiplos clientes de forma eficiente.
Java I/O: Pacotes java.io (como DataInputStream e DataOutputStream) usados para leitura e escrita de mensagens em formato UTF-8 entre o servidor e os clientes.

Funcionalidades

Suporte a múltiplos usuários: O servidor gerencia várias conexões simultâneas, permitindo que mais de dois clientes participem do chat.
Mensagens bidirecionais: Tanto os clientes quanto o servidor podem enviar mensagens, que são exibidas com prefixos [nome_do_usuário]: ou [Servidor]:.
Identificação por nome de usuário: Cada cliente escolhe um nome ao conectar, usado para identificar suas mensagens.
Notificações de entrada e saída: Quando um cliente entra ou sai do chat, todos os outros são notificados.
Timeout: Os clientes têm um timeout de 30 segundos para operações de leitura, evitando bloqueios indefinidos.
Comando de saída: Digitar sair em um cliente o desconecta do servidor.

Arquivos

ThreadPoolTCPServer.java: Servidor TCP que escuta na porta 6666, aceita conexões de clientes e permite que o administrador envie mensagens via console.
ClientHandler.java: Classe auxiliar que gerencia a comunicação com cada cliente, armazena a lista de clientes conectados e realiza o broadcast de mensagens.
SimpleTCPClient.java: Cliente TCP que se conecta ao servidor, envia mensagens digitadas pelo usuário e exibe mensagens recebidas em tempo real.

Como Compilar e Executar
Pré-requisitos

Java Development Kit (JDK) instalado.
Um terminal ou IDE que suporte múltiplas instâncias (para testar vários clientes).

Passos

Compilar os arquivos:No diretório onde estão os arquivos, execute:
javac ThreadPoolTCPServer.java ClientHandler.java SimpleTCPClient.java


Iniciar o servidor:Em um terminal, execute:
java ThreadPoolTCPServer

O servidor iniciará na porta 6666 e exibirá:
Servidor iniciado em: 127.0.0.1:6666
Aguardando conexão...


Iniciar os clientes:Abra múltiplos terminais (pelo menos 3 para testar o suporte a múltiplos usuários). Em cada terminal, execute:
java SimpleTCPClient


Digite um nome de usuário único (ex.: Alice, Bob, Carol).
Envie mensagens digitando no prompt Mensagem:.
Digite sair para desconectar.


Enviar mensagens do servidor:No terminal do servidor, digite mensagens no prompt Mensagem do servidor:. Elas serão enviadas a todos os clientes com o prefixo [Servidor]:.


Teste de Múltiplos Usuários
Para verificar o suporte a múltiplos usuários:

Inicie o servidor.
Inicie pelo menos 3 clientes em terminais separados, cada um com um nome de usuário diferente.
Envie mensagens de cada cliente e do servidor.
Verifique se:
Todos os clientes recebem as mensagens dos outros (exceto as próprias).
As mensagens do servidor aparecem em todos os clientes.
Quando um cliente digita sair ou é desconectado, os outros recebem uma notificação (ex.: Carol saiu do chat!).


Teste com 4 ou 5 clientes para confirmar a escalabilidade.

Exemplo de Saída

Servidor:Servidor iniciado em: 127.0.0.1:6666
Aguardando conexão...
Conexão estabelecida com: /127.0.0.1:50001
Usuário conectado: Alice (/127.0.0.1:50001)
Broadcast: Alice entrou no chat!
Conexão estabelecida com: /127.0.0.1:50002
Usuário conectado: Bob (/127.0.0.1:50002)
Broadcast: Bob entrou no chat!
Conexão estabelecida com: /127.0.0.1:50003
Usuário conectado: Carol (/127.0.0.1:50003)
Broadcast: Carol entrou no chat!
Mensagem do servidor: Bem-vindos ao chat!
Broadcast do servidor: [Servidor]: Bem-vindos ao chat!
Broadcast: [Alice]: Olá, todos!
Broadcast: [Bob]: Oi, Alice!
Broadcast: [Carol]: Oi, pessoal!
Broadcast: Carol saiu do chat!


Cliente Alice:Digite seu nome de usuário: Alice
[C1] Conectado como: Alice em 127.0.0.1:6666
Bob entrou no chat!
Carol entrou no chat!
[Servidor]: Bem-vindos ao chat!
Mensagem: Olá, todos!
[Bob]: Oi, Alice!
[Carol]: Oi, pessoal!
Carol saiu do chat!


Cliente Bob:Digite seu nome de usuário: Bob
[C1] Conectado como: Bob em 127.0.0.1:6666
Alice entrou no chat!
Carol entrou no chat!
[Servidor]: Bem-vindos ao chat!
[Alice]: Olá, todos!
Mensagem: Oi, Alice!
[Carol]: Oi, pessoal!
Carol saiu do chat!



Modificações Realizadas

Base: O projeto foi baseado no código da Prática 1 (ThreadPoolTCPServer.java, ClientHandler.java, SimpleTCPClient.java).
Suporte a múltiplos usuários: O ClientHandler usa uma lista estática para armazenar todos os clientes conectados e um método broadcast para enviar mensagens a todos, exceto o remetente.
Mensagens do servidor: Adicionada uma thread no ThreadPoolTCPServer que lê mensagens do console e as envia a todos os clientes via ClientHandler.serverBroadcast.
Cliente assíncrono: O SimpleTCPClient usa duas threads: uma para ler mensagens do servidor e outra para enviar mensagens digitadas, permitindo comunicação em tempo real.
Robustez:
Timeout de 30 segundos no cliente para evitar bloqueios.
Sincronização na lista de clientes para evitar problemas de concorrência.
Tratamento de erros para conexões e desconexões.


Formato das mensagens:
Clientes: [nome]: mensagem
Servidor: [Servidor]: mensagem
Notificações: nome entrou no chat! ou nome saiu do chat!



Possíveis Melhorias

Adicionar comandos como /list para listar usuários conectados ou /dm <usuário> <mensagem> para mensagens privadas.
Implementar timeout de inatividade para desconectar clientes ociosos.
Criar uma interface gráfica (usando JavaFX ou Swing) para substituir a linha de comando.
Adicionar criptografia para maior segurança nas mensagens.
