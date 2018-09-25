package org.vatalu.nativeSocket.UDP;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.Scanner;

public class UDPStarter {
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
        UDPServer udpServer = new UDPServer();
        int port = 9876;
        Optional<DatagramSocket> optionalDatagramSocket = udpServer.open(port);
        optionalDatagramSocket.ifPresent(socket -> {
            System.out.println("[UDP server "+port+"] start");
            while (true) {
                DatagramPacket datagramPacket = udpServer.receive(socket);
                String message = new String(datagramPacket.getData());
                InetAddress IPAddress = datagramPacket.getAddress();
                int port2 = datagramPacket.getPort();
                System.out.println("[UDP server "+port+"] receive"+ message);
                String capitalizedSentence = message.toUpperCase() + "\n";
                udpServer.send(capitalizedSentence, socket, IPAddress, port2);
            }
        });
    }

    public static void startClient() {
        UDPClient udpClient = new UDPClient();
        Optional<DatagramSocket> optionalDatagramPacket = udpClient.open();
        optionalDatagramPacket.ifPresent(socket -> {
            System.out.println("[UDP client] start");
            Scanner scanner = new Scanner(System.in);
            while (scanner.hasNext()) {
                String message = scanner.nextLine();
                try {
                    InetAddress IPAddress = InetAddress.getByName("localhost");
                    udpClient.send(message, socket, IPAddress, 9876);
                    String sentence = udpClient.receive(socket);
                    System.out.println("[UDP client] receive "+sentence);
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
            }
            socket.close();
        });
    }
}