package com.photon.DB;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class UDPSocket {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket(9876); // UDP socket on port 9876
        byte[] receiveData = new byte[1024];
        byte[] sendData;

        while (true) {
            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);
            System.out.println("Waiting for a client to send data...");
            socket.receive(receivePacket);
            String sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
            System.out.println("Received: " + sentence);

            // Process the received data (for example, you can call your PlayerDAO here)
            // Assuming sentence is something like "create|1|codename"
            String[] tokens = sentence.split("\\|");

            if (tokens[0].equals("create")) {
                int id = Integer.parseInt(tokens[1]);
                String codename = tokens[2];

                PostgreSQL postgreSQL = PostgreSQL.getInstance();
                PlayerDAO playerDAO = new PlayerDAO(postgreSQL);
                boolean success = playerDAO.createPlayer(id, codename);

                sendData = ("Player creation " + (success ? "succeeded" : "failed")).getBytes();
            } else {
                sendData = "Invalid command".getBytes();
            }

            InetAddress clientAddress = receivePacket.getAddress();
            int clientPort = receivePacket.getPort();
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, clientAddress, clientPort);
            socket.send(sendPacket);
        }
    }
}
