package org.vatalu.nativeSocket.UDP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Optional;

class UDPClient {
    public static void main(String args[]) throws Exception {
        BufferedReader inFromUser;
        inFromUser = new BufferedReader(new InputStreamReader(System.in));
        DatagramSocket clientSocket = new DatagramSocket();
        InetAddress IPAddress = InetAddress.getByName("hostname");
        byte[] sendData = new byte[1024];
        byte[] receiveData = new byte[1024];
        String sentence = inFromUser.readLine();
        sendData = sentence.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, 9876);
        clientSocket.send(sendPacket);
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        clientSocket.receive(receivePacket);
        String modifiedSentence = new String(receivePacket.getData());
        System.out.println("FROM SERVER:" + modifiedSentence);
        clientSocket.close();
    }

    public Optional<DatagramSocket> open() {
        try {
            return Optional.of(new DatagramSocket());
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void send(String message, DatagramSocket socket, InetAddress inetAddress, int port) {
        byte[] sendData = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, inetAddress, port);
        try {
            socket.send(sendPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receive(DatagramSocket scoket){
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        try {
            scoket.receive(receivePacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(receivePacket.getData());
    }

    public void close(DatagramSocket socket) {
        socket.close();
    }
}