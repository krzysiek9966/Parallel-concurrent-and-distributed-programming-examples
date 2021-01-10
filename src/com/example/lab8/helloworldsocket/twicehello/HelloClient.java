package com.example.lab8.helloworldsocket.twicehello;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class HelloClient {
    public static void main(String[] args) throws IOException, InterruptedException {
        Socket socket = new Socket("127.0.0.1", 6000);
//        Socket socket = new Socket(InetAddress.getLocalHost(), 6000);

        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        BufferedReader socketBufferedReader = new BufferedReader(new
                InputStreamReader(socket.getInputStream()));

        output.writeBytes("Hello, " + "\n");
        output.flush();
        output.writeBytes("Hello, " + "\n");
        output.flush();

        String response = socketBufferedReader.readLine();
        System.out.println("Sever response: " + response);
        output.writeBytes("Thank you!\n");
        output.flush();
        output.close();
        socketBufferedReader.close();
        socket.close();
    }
}
