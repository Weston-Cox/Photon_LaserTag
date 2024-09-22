package com.photon.DB;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSocket {
    private DatagramSocket broadcastSocket;
    private DatagramSocket receiveSocket;
    private InetAddress address;
    private int broadcastPort;
    private int receivePort;

    public UDPSocket(String address, int broadcastPort, int receivePort) throws IOException {
        this.address = InetAddress.getByName(address);
        this.broadcastPort = broadcastPort;
        this.receivePort = receivePort;
        this.broadcastSocket = new DatagramSocket();
        this.receiveSocket = new DatagramSocket(receivePort);
    }

    public void broadcast(String message) throws IOException {
        byte[] buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, broadcastPort);
        broadcastSocket.send(packet);
    }

    public String receive() throws IOException {
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
        receiveSocket.receive(packet);
        return new String(packet.getData(), 0, packet.getLength());
    }

    public void close() {
        broadcastSocket.close();
        receiveSocket.close();
    }

    // public static void main(String[] args) throws IOException {
    //     // Initialize database connection
    //     PostgreSQL postgreSQL = new PostgreSQL("database_url", "username", "password");
    //     PlayerDAO playerDAO = new PlayerDAO(postgreSQL);

    //     UDPSocket udpSocket = new UDPSocket("localhost", 7500, 7501);
        
    //     while (true) {
    //         String receivedData = udpSocket.receive();
    //         String[] parts = receivedData.split(":");
    //         int transmittingPlayerId = Integer.parseInt(parts[0]);
    //         int hitPlayerId = Integer.parseInt(parts[1]);

    //         // Process the received data
    //         System.out.println("Player " + transmittingPlayerId + " hit player " + hitPlayerId);

    //         // Broadcast the hit player id
    //         udpSocket.broadcast(String.valueOf(hitPlayerId));
    //     }
    // }
}