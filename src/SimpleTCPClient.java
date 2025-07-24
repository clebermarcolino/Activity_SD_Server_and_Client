import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class SimpleTCPClient {
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;
    private boolean running;

    public void start(String serverIp, int serverPort, String username) throws IOException {
        socket = new Socket(serverIp, serverPort);
        input = new DataInputStream(socket.getInputStream());
        output = new DataOutputStream(socket.getOutputStream());
        running = true;

        // Envia o nome de usuário ao servidor
        output.writeUTF(username);
        System.out.println("[C1] Conectado como: " + username + " em " + serverIp + ":" + serverPort);

        // Thread para ler mensagens do servidor
        Thread readerThread = new Thread(() -> {
            try {
                while (running) {
                    String message = input.readUTF();
                    System.out.println(message);
                }
            } catch (IOException e) {
                if (running) {
                    System.out.println("Erro ao receber mensagem: " + e.getMessage());
                }
            }
        });
        readerThread.start();

        // Thread principal para enviar mensagens
        Scanner scanner = new Scanner(System.in);
        while (running) {
            System.out.print("Mensagem: ");
            String msg = scanner.nextLine();
            output.writeUTF(msg);
            if (msg.equalsIgnoreCase("sair")) {
                running = false;
            }
        }
        scanner.close();
    }

    public void stop() {
        running = false;
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            System.out.println("Erro ao fechar cliente: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        String serverIp = "127.0.0.1";
        int serverPort = 6666;
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite seu nome de usuário: ");
        String username = scanner.nextLine();

        try {
            SimpleTCPClient client = new SimpleTCPClient();
            client.start(serverIp, serverPort, username);
            client.stop();
        } catch (IOException e) {
            System.out.println("Erro no cliente: " + e.getMessage());
        }
    }
}