package com.example.lab9.helloworldthreadserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class WorldThreadServer {
    static class ServerThread extends Thread {
        Socket connection;

        public ServerThread(Socket connection) {
            this.connection = connection;
        }

        @Override
        public void run() {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                System.out.println("Accepted connection");
                int i = 0;
                while (i < 10) {
                    String hello = br.readLine();
                    System.out.println("Client sends: " + hello);
                    dataOutputStream.writeBytes(" world!" + System.lineSeparator());
                    dataOutputStream.flush();
                    i++;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6000);
        System.out.println("Started server at: " + serverSocket.getLocalSocketAddress());
        System.out.println(serverSocket.getInetAddress());
        int noClients = 0;
        ArrayList<Socket> connections = new ArrayList<>();
        while (noClients < 10) {
            Socket connection = serverSocket.accept();
            connections.add(connection);
            new ServerThread(connection).start();
            noClients++;
        }
        for (Socket s : connections) {
            s.getOutputStream().close();
            s.getInputStream().close();
            s.close();
        }
        serverSocket.close();
    }
}