package org.vatalu.nativeSocket.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Optional;

class UDPServer {
    public static void main(String args[]) throws Exception {
        DatagramSocket serverSocket = new DatagramSocket(9876);
        byte[] receiveData = new byte[1024];
        byte[] sendData = new byte[1024];
        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            serverSocket.receive(receivePacket);
            String sentence = new String(receivePacket.getData());
            InetAddress IPAddress = receivePacket.getAddress();
            int port = receivePacket.getPort();
            String capitalizedSentence = sentence.toUpperCase();
            sendData = capitalizedSentence.getBytes();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, IPAddress, port);
            serverSocket.send(sendPacket);
        }
    }

    public Optional<DatagramSocket> open(int port) {
        try {
            return Optional.of(new DatagramSocket(port));
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void send(DatagramSocket socket, InetAddress ip, int port, String sentence) {
        byte[] message = sentence.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(message, message.length, ip, port);
        try {
            socket.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String receive(DatagramSocket socket) {
        byte[] receiveData = new byte[1024];
        DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
        try {
            socket.receive(receivePacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(receivePacket.getData());
    }
}