package org.vatalu.nativeSocket.TCP;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Optional;

public class TCPServer {
    public static void close(Socket socket) {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Optional<Socket> open(ServerSocket serverSocket) {
        try {
            return Optional.of(serverSocket.accept());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static void send(Socket socket, String sentence) {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeBytes(sentence);
        } catch (IOException e) {
            e.printStackTrace();
            close(socket);
        }

    }

    public static String receive(Socket socket) {
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