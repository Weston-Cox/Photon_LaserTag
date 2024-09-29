package com.photon.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServer implements Runnable{
    private DatagramSocket socket = new DatagramSocket(7501);
    private boolean running;
    private byte[] buffer = new byte[65535];

    public UDPServer(int port) throws IOException {
        socket = new DatagramSocket(port);
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet); // This is a blocking call
                
                String receivedMessage = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Received: " + receivedMessage);

                // Handle the received message (parse, process, etc.)
            } catch (IOException e) {
                e.printStackTrace();
            }
            buffer = new byte[65535];
        }
        socket.close();
    }

    public void stop() {
        running = false;
    }

    // Helper method that converts the byte array into a string
    // Grabbed from Professor Strother's GitHub.
    // https://github.com/jstrother123/photon-main/blob/main/udp_files/udpBaseServer_2.java
    // public static StringBuilder data(byte[] a)
	// {
	// 	if (a == null)
	// 		return null;
	// 	StringBuilder ret = new StringBuilder();
	// 	int i = 0;
	// 	while (a[i] != 0)
	// 	{
	// 		ret.append((char) a[i]);
	// 		i++;
	// 	}
	// 	return ret;
	// }

}
