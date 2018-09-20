package org.vatalu.nativeSocket.TCP;

import java.net.Socket;
import java.util.Optional;
import java.util.Scanner;

public class TCPStarter {
    public static void main(String[] args) {
        System.out.println("Please input '0' or '1' to start a server or a client.");
        System.out.println("Before starting a client, make sure a server is already running at the same PC.");
        System.out.println("  0: server");
        System.out.println("  1: client");
        System.out.println("  others: close the program.");
        Scanner scanner = new Scanner(System.in);
        String input = scanner.next();
        switch (input) {
            case "0":
                startServer();
                break;
            case "1":
                startClient();
                break;
            default:
                break;
        }
        scanner.close();
    }

    public static void startServer() {
        TCPServer tcpServer = new TCPServer();
        Optional<Socket> opSocket = tcpServer.open(8080);
        if (opSocket.isPresent()) {
            System.out.println("[TCP server 8080]: start");
            while (true) {
                Socket socket = opSocket.get();
                String message = tcpServer.receive(socket);
                if (message != null) {
                    System.out.println("[TCP server 8080]: " + message);
                    tcpServer.send(socket, message.toUpperCase() + "\n");
                }
            }
        }
    }

    public static void startClient() {
        TCPClient tcpClient = new TCPClient();
        Optional<Socket> opSocket = tcpClient.open("localhost", 8080);
        if (opSocket.isPresent()) {
            System.out.println("[TCP client 8080]: start");
            Socket socket = opSocket.get();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                tcpClient.send(socket, scanner.nextLine());
                tcpClient.receive(socket).ifPresent((message)-> System.out.println("[TCP client 8080]: "+ message));
            }
            tcpClient.close(socket);
            System.out.println("[TCP client 8080]: close");
            scanner.close();
        } else {
            System.out.println("connnect failed");
        }
    }
}