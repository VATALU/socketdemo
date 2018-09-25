package org.vatalu.nativeSocket.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Optional;

class UDPServer {

    public Optional<DatagramSocket> open(int port) {
        try {
            return Optional.of(new DatagramSocket(port));
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

    public void send(String sentence, DatagramSocket socket, InetAddress ip, int port) {
        byte[] message = sentence.getBytes();
        DatagramPacket datagramPacket = new DatagramPacket(message, message.length, ip, port);
        try {
            socket.send(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public DatagramPacket receive(DatagramSocket socket) {
        byte[] receiveData = new byte[1024];
        DatagramPacket datagramPacket = new DatagramPacket(receiveData, receiveData.length);
        try {
            socket.receive(datagramPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return datagramPacket;
    }
}