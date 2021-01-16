package com.example.lab9.helloworldthreadserver;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class HelloClient {

    static class Client extends Thread {
        String name;

        public Client(String name) {
            this.name = name;
        }

        @Override
        public void run() {
            try {
                Socket socket = new Socket("127.0.0.1", 6000);
                DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                BufferedReader socketBufferedReader = new BufferedReader(new
                        InputStreamReader(socket.getInputStream()));
                for (int i = 0; i < 10; i++) {
                    output.writeBytes("Hello, " + System.lineSeparator());
                    output.flush();
                    String response = socketBufferedReader.readLine();
                    System.out.println(this.name + " Sever responsed: " + response);
                }
                output.close();
                socketBufferedReader.close();
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        new Client("1").start();
        new Client("2").start();
        new Client("3").start();
        new Client("4").start();
        new Client("5").start();
    }
}