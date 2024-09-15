package com.photon.DB;

import java.net.DatagramSocket;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.io.IOException;

public class UDPSocket {
    private DatagramSocket socket;
    private InetAddress address;
    private int port;

    public UDPSocket(String address, int port) throws IOException {
        this.address = InetAddress.getByName(address);
        this.port = port;
        this.socket = new DatagramSocket();
    }

    public void send(String message) throws IOException {
        byte[] buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, port);
        socket.send(packet);
    }

    public String receive() throws IOException {
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        socket.receive(packet);
        return new String(packet.getData(), 0, packet.getLength());
    }

    public void close() {
        socket.close();
    }

    public static void main(String[] args) {
        try {
            UDPSocket udpSocket = new UDPSocket("localhost", 9876);
            udpSocket.send("Hello, World!");
            String response = udpSocket.receive();
            System.out.println("Received: " + response);
            udpSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}