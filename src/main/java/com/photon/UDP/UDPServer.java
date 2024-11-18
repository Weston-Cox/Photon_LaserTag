package com.photon.UDP;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.concurrent.CountDownLatch;

import com.photon.Helpers.UDPMessageCallback;

public class UDPServer implements Runnable{
    private DatagramSocket socket;
    private boolean running;
    private byte[] buffer = new byte[4096];
    private UDPMessageCallback callback;
    private final CountDownLatch latch = new CountDownLatch(1);

    public UDPServer(int port, UDPMessageCallback callback) throws IOException {
        socket = new DatagramSocket(port);
        this.callback = callback;
    }

    @Override
    public void run() {
        running = true;

        try {
            latch.await(); // Wait for the callback to be set
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return;
        }

        while (running) {
            try {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet); // This is a blocking call
                
                String receivedMessage = new String(packet.getData(), 0, packet.getLength());
                // System.out.println("Received: " + receivedMessage);

                if (callback != null) {
                    callback.onMessageReceived(receivedMessage);
                }

                // Handle the received message (parse, process, etc.)
            } catch (SocketException e) {
                if (!running) {
                    System.out.println("Socket closed");
                    break;
                } else {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                System.out.println("Error receiving packet: " + e.getMessage());
            }
            buffer = new byte[4096];
        }
        socket.close();
    }

    public void stop() {
        running = false;
        socket.close();
    }

    public void setCallback(UDPMessageCallback callback) {
        this.callback = callback;
        latch.countDown();
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
