package com.photon.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class UDPClient {
    private DatagramSocket socket;
    private InetAddress address;
    private int broadcastPort = 7500;

    public UDPClient() throws IOException {
        socket = new DatagramSocket();
        try {
            InetAddress localhost = InetAddress.getLocalHost();

            this.address = localhost;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        // address = InetAddress.getByName("192.168.0.131");
    }

    public void send(String message) throws IOException {
        byte[] buffer = message.getBytes();
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, address, broadcastPort);
        socket.send(packet);
    }

    public void close() {
        if (!socket.isClosed())
            socket.close();
        
        return;
    }

}
