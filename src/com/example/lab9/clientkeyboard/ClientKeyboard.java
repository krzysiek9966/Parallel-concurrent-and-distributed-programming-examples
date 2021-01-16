package com.example.lab9.clientkeyboard;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketException;

public class ClientKeyboard {
    public static void main(String[] args) throws IOException {
        BufferedReader keyboardBufferedReader = new BufferedReader(new InputStreamReader(System.in));
        Socket socket = new Socket("127.0.0.1", 6000);
        DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        BufferedReader socketBufferedReader = new BufferedReader(new
                InputStreamReader(socket.getInputStream()));
        String response = socketBufferedReader.readLine();
        System.out.println("Sever responsed: " + response);
        while (true) {
            response = "";
            try {
                String command = keyboardBufferedReader.readLine();
                switch (command) {
                    case "end": {
                        socket.close();
                        System.out.println("stopped!");
                        return;
                    }
                    case "all files": {
                        output.writeBytes("all files" + System.lineSeparator());
                        output.flush();
                        response = socketBufferedReader.readLine();
                        System.out.println("Sever responsed: " + response);
                        break;
                    }
                    case "new file": {
                        output.writeBytes("new file" + System.lineSeparator());
                        output.flush();

                        System.out.println("? get file name: ");
                        String fileName = keyboardBufferedReader.readLine();
                        output.writeBytes(fileName + System.lineSeparator());
                        output.flush();

                        System.out.println("? get file content: ");
                        String fileContent = keyboardBufferedReader.readLine();
                        output.writeBytes(fileContent + System.lineSeparator());
                        output.flush();

                        response = socketBufferedReader.readLine();
                        System.out.println("Sever responsed: " + response);
                        break;
                    }
                    case "get file": {
                        output.writeBytes("get file" + System.lineSeparator());
                        output.flush();

                        System.out.println("? get file name: ");
                        String fileName = keyboardBufferedReader.readLine();
                        output.writeBytes(fileName + System.lineSeparator());
                        output.flush();

                        response = socketBufferedReader.readLine();
                        System.out.println("Sever responsed: " + response);
                        break;
                    }
                }
            } catch (SocketException ex) {
                System.out.println("Server stopped connection!");
                break;
            } catch (IOException e) {
                System.out.println("Input output exception!");
                break;
            }
        }
    }
}
