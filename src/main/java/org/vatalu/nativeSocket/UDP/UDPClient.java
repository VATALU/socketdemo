package org.vatalu.nativeSocket.UDP;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.util.Optional;
import java.util.Scanner;

class UDPClient {
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