package org.vatalu.nativeSocket.TCP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

public class TCPServer {
    public static void main(String[] args) {
        TCPServer tcpServer = new TCPServer();
        Optional<Socket> opSocket = tcpServer.open(8080);
        if (opSocket.isPresent()) {
            while (true) {
                Socket socket = opSocket.get();
                String message = tcpServer.receive(socket);
                if (message != null) {
                    System.out.println(message);
                    tcpServer.send(socket,message.toUpperCase()+"\n");
                }
            }
        }
    }

    public void close(Socket socket) {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<Socket> open(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            return Optional.of(serverSocket.accept());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void send(Socket socket, String sentence) {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeBytes(sentence);
        } catch (IOException e) {
            e.printStackTrace();
            close(socket);
        }

    }

    public String receive(Socket socket) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
            close(socket);
        }
        return null;
    }
}