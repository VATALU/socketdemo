package org.vatalu.nativeSocket.TCP;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Optional;
import java.util.Scanner;

public class TCPClient {
    public static Optional<Socket> open(String host, int port) {
        try {
            Socket socket = new Socket(host, port);
            return Optional.of(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public static void close(Socket socket) {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void send(Socket socket, String sentence) {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeBytes(sentence + '\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String receive(Socket socket) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}