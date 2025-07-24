import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

class ClientHandler extends Thread {
    private DataInputStream in;
    private DataOutputStream out;
    private Socket clientSocket;
    private String username;
    private static List<ClientHandler> clients = new ArrayList<>();

    public ClientHandler(Socket aClientSocket) {
        try {
            clientSocket = aClientSocket;
            in = new DataInputStream(clientSocket.getInputStream());
            out = new DataOutputStream(clientSocket.getOutputStream());
            synchronized (clients) {
                clients.add(this);
            }
        } catch (IOException e) {
            System.out.println("Erro de conexão: " + e.getMessage());
        }
    }

    public void run() {
        try {
            // Lê o nome de usuário enviado pelo cliente
            username = in.readUTF();
            System.out.println("Usuário conectado: " + username + " (" + clientSocket.getRemoteSocketAddress() + ")");
            broadcast(username + " entrou no chat!", this);

            // Loop para ler mensagens do cliente
            while (true) {
                String message = in.readUTF();
                if (message.equalsIgnoreCase("sair")) {
                    break;
                }
                broadcast("[" + username + "]: " + message, this);
            }
        } catch (IOException e) {
            System.out.println("Erro com " + username + ": " + e.getMessage());
        } finally {
            // Remove o cliente da lista e notifica os outros
            synchronized (clients) {
                clients.remove(this);
            }
            broadcast(username + " saiu do chat!", this);
            try {
                clientSocket.close();
            } catch (IOException e) {
                System.out.println("Falha ao fechar conexão de " + username + ": " + e.getMessage());
            }
        }
    }

    // Envia uma mensagem para todos os clientes, exceto o remetente
    private void broadcast(String message, ClientHandler sender) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                if (client != sender) {
                    try {
                        client.out.writeUTF(message);
                    } catch (IOException e) {
                        System.out.println("Erro ao enviar para " + client.username + ": " + e.getMessage());
                    }
                }
            }
        }
        System.out.println("Broadcast: " + message);
    }

    // Método para enviar mensagem do servidor para todos os clientes
    public static void serverBroadcast(String message) {
        synchronized (clients) {
            for (ClientHandler client : clients) {
                try {
                    client.out.writeUTF("[Servidor]: " + message);
                } catch (IOException e) {
                    System.out.println("Erro ao enviar mensagem do servidor para " + client.username + ": " + e.getMessage());
                }
            }
        }
        System.out.println("Broadcast do servidor: [Servidor]: " + message);
    }
}