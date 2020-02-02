package ru.ifmo.rain.krivopaltsev.hello;

import info.kgeorgiy.java.advanced.hello.HelloServer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.PortUnreachableException;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HelloUDPServer implements HelloServer {
    private DatagramSocket socket;
    private ExecutorService workers;
    private ExecutorService listener;
    private int bufferSize;


    @Override
    public void start(int port, int threads) {
        try {
            socket = new DatagramSocket(port);
            bufferSize = socket.getReceiveBufferSize();
        } catch (SocketException e) {
            e.printStackTrace();
            return;
        }
        listener = Executors.newSingleThreadExecutor();
        workers = Executors.newFixedThreadPool(threads);
        listener.submit(this::waitRequest);
    }

    private void sendResponse(DatagramPacket packet) {
        String message = new String(packet.getData(), packet.getOffset(), packet.getLength(), StandardCharsets.UTF_8);
        String newMessage = "Hello, " + message;
        DatagramPacket response = new DatagramPacket(newMessage.getBytes(), 0, newMessage.getBytes().length, packet.getSocketAddress());
        try {
            socket.send(response);
        } catch (PortUnreachableException e) {
            System.out.println("Socket is connected to a currently unreachable destination");
        } catch (SocketException e) {
            System.out.println("Error with socket");
        } catch (IOException e) {
            System.out.println("IOException while send");
        }
    }

    private void waitRequest() {
        while (!socket.isClosed() && !Thread.currentThread().isInterrupted()) {
            DatagramPacket packet = new DatagramPacket(new byte[bufferSize], bufferSize);
            try {
                socket.receive(packet);
                workers.submit(() -> sendResponse(packet));
            } catch (PortUnreachableException e) {
                System.out.println("Port unreachable on socket: " + socket.toString());
            } catch (SocketException e) {
                System.out.println("Error with socket");
            } catch (IOException e) {
                System.out.println("IOException while send socket " + socket.toString());
            }
        }
    }


    @Override
    public void close() {
        socket.close();
        listener.shutdownNow();
        workers.shutdownNow();
    }

    static public void main(String[] args) {
        int port, threadsNum;
        try {
            port = Integer.parseInt(args[0]);
            threadsNum = Integer.parseInt(args[1]);
        } catch (Exception e) {
            System.out.println("Error: expected 2 not null arguments : <port> <threadsNum>");
            return;
        }

        try (HelloServer server = new HelloUDPServer()) {
            server.start(port, threadsNum);
        }
    }
}
