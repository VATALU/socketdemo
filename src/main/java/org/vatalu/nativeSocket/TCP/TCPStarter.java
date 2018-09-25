package org.vatalu.nativeSocket.TCP;

import java.io.IOException;
import java.net.ServerSocket;
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
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(8080);
            System.out.println("[TCP server 8080]: start");
            while (true) {
                Optional<Socket> opSocket = TCPServer.open(serverSocket);
                if (opSocket.isPresent()) {
                    boolean islogin = false;
                    Socket socket = opSocket.get();
                    while (true) {
                        String message = TCPServer.receive(socket);
                        if (message != null) {
                            System.out.println("[TCP server 8080]: " + message);
                            if ("disconnect".equals(message)) {
                                TCPServer.close(socket);
                                System.out.println("disconnected");
                                break;
                            }
                            if (islogin) {
                                TCPServer.send(socket, message.toUpperCase() + "\n");
                            } else {
                                if (message.startsWith("username")) {
                                    if (message.contains("username:") && message.contains("password:")) {
                                        islogin = true;
                                        String[] usernames = message.split(";")[0].split(":");
                                        TCPServer.send(socket, "hello " + usernames[1] + "\n");
                                    } else {
                                        System.out.println("[TCP server 8080]: username or password error, disconnect..");
                                        TCPServer.send(socket, "username or password error\n");
                                        TCPServer.send(socket, "disconnect\n");
                                        TCPServer.close(socket);
                                        System.out.println("disconnect");
                                        break;
                                    }
                                } else {
                                    TCPServer.send(socket, "please login first\n");
                                }

                            }
                        }
                    }

                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void startClient() {
        Optional<Socket> opSocket = TCPClient.open("localhost", 8080);
        if (opSocket.isPresent()) {
            System.out.println("[TCP client 8080]: start");
            Socket socket = opSocket.get();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String sendMessage = scanner.nextLine();
                switch (sendMessage) {
                    case "disconnect":
                        TCPServer.send(socket, "disconnect");
                        TCPClient.close(socket);
                        System.out.println("close connection");
                        return;
                    default:
                        TCPClient.send(socket, sendMessage);
                        String message = TCPClient.receive(socket);
                        if (message.contains("error")) {
                            TCPClient.close(socket);
                            System.out.println("close connection");
                            return;
                        } else {
                            System.out.println("[TCP Client 8080] " + message);
                        }
                        break;
                }

            }
            TCPClient.close(socket);
            System.out.println("[TCP client 8080]: close");
            scanner.close();
        } else {
            System.out.println("connnect failed");
        }
    }
}