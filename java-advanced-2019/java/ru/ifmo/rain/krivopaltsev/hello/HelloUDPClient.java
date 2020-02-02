package ru.ifmo.rain.krivopaltsev.hello;

import info.kgeorgiy.java.advanced.hello.HelloClient;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class HelloUDPClient implements HelloClient {
    private static int TIMEOUT = 100;

    public void run(String host, int port, String prefix, int threads, int requests) {
        InetSocketAddress inetSocketAddress = new InetSocketAddress(host, port);
        ExecutorService workers = Executors.newFixedThreadPool(threads);

        for (int i = 0; i < threads; i++) {
            final int threadNumber = i;
            workers.submit(() -> {
                try (DatagramSocket datagramSocket = new DatagramSocket()) {
                    datagramSocket.setSoTimeout(TIMEOUT);

                    for (int requestNumber = 0; requestNumber < requests; requestNumber++) {
                        String request = prefix + threadNumber + "_" + requestNumber;
                        DatagramPacket sendDatagramPacket = new DatagramPacket(request.getBytes(), 0, request.getBytes().length, inetSocketAddress);
                        int bufferSize = datagramSocket.getReceiveBufferSize();
                        DatagramPacket receive = new DatagramPacket(new byte[bufferSize], bufferSize);
                        while (true) {
                            try {
                                datagramSocket.send(sendDatagramPacket);
                                datagramSocket.receive(receive);
                                String receivedMsg = new String(receive.getData(), receive.getOffset(), receive.getLength(), StandardCharsets.UTF_8);
                                if (receivedMsg.contains(request)) {
                                    System.out.println("Requesting to " + host + ", request : " + request);
                                    System.out.println("Response from " + host + ", response : " + receivedMsg);
                                    break;
                                }
                            } catch (PortUnreachableException e) {
                                System.err.println("Socket is connected to a currently unreachable destination");
                            } catch (SocketTimeoutException e) {
                                System.err.println("Socket timeout error (timeout was " + TIMEOUT + ")");
                            } catch (IOException e) {
                                System.err.println("IOException while send");
                            }
                        }
                    }
                } catch (SocketException e) {
                    System.err.println("Error with socket");
                }

            });
        }
        workers.shutdown();
        try {
            workers.awaitTermination(threads * requests * TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        String host, prefix;
        int port, threadsNum, requestsNum;

        try {
            host = args[0];
            port = Integer.parseInt(args[1]);
            prefix = args[2];
            threadsNum = Integer.parseInt(args[3]);
            requestsNum = Integer.parseInt(args[4]);
        } catch (Exception e) {
            System.out.println("Error: expected 5 not null arguments :: <host> <port> <prefix> <threadsNum> <requestsNum>");
            return;
        }
        new HelloUDPClient().run(host, port, prefix, threadsNum, requestsNum);
    }
}
