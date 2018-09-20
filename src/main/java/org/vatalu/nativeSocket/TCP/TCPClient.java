package org.vatalu.nativeSocket.TCP;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Optional;
import java.util.Scanner;

public class TCPClient{
    public static void main(String[] args) {
        TCPClient tcpClient = new TCPClient();
        Optional<Socket> opSocket = tcpClient.open("localhost",8080);
        if (opSocket.isPresent()) {
            Socket socket = opSocket.get();
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                tcpClient.send(socket, scanner.nextLine());
                tcpClient.receive(socket).ifPresent(System.out::println);
            }
            tcpClient.close(socket);
        } else {
            System.out.println("connnect failed");
        }
    }

    public  Optional<Socket> open(String host, int port) {
        try {
            Socket socket = new Socket(host, port);
            return Optional.of(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void close(Socket socket) {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void send(Socket socket, String sentence) {
        try {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeBytes(sentence + '\n');
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Optional<String> receive(Socket socket) {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            return Optional.of(in.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }
}