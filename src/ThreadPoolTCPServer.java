import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolTCPServer {
    public static void main(String args[]) {
        ExecutorService threadPool = Executors.newFixedThreadPool(10);
        ServerSocket serverSocket = null;
        try {
            int serverPort = 6666;
            serverSocket = new ServerSocket(serverPort);
            System.out.println("Servidor iniciado em: " + InetAddress.getLocalHost() + ":" + serverPort);

            // Thread para ler mensagens do servidor (console)
            Thread serverConsole = new Thread(() -> {
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    System.out.print("Mensagem do servidor: ");
                    String message = scanner.nextLine();
                    if (message.equalsIgnoreCase("sair")) {
                        break;
                    }
                    ClientHandler.serverBroadcast(message);
                }
                scanner.close();
            });
            serverConsole.start();

            // Loop para aceitar conexões de clientes
            while (serverSocket.isBound()) {
                System.out.println("Aguardando conexão...");
                Socket clientSocket = serverSocket.accept();
                System.out.println("Conexão estabelecida com: " + clientSocket.getInetAddress() + ":" + clientSocket.getPort());
                ClientHandler handler = new ClientHandler(clientSocket);
                threadPool.submit(handler);
            }
        } catch (IOException e) {
            System.out.println("Erro no servidor: " + e.getMessage());
        } finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                }
            } catch (IOException e) {
                System.out.println("Erro ao fechar servidor: " + e.getMessage());
            }
            threadPool.shutdown();
        }
    }
}