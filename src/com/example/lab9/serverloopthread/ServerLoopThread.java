package com.example.lab9.serverloopthread;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicBoolean;

public class ServerLoopThread {
    static class ServerThread extends Thread {
        AtomicBoolean connectionNonEnded = new AtomicBoolean(true);

        Socket connection;

        public ServerThread(Socket connection) {
            this.connection = connection;
        }

        private static List<String> readFile(String fileName) {
            File file = new File(fileName);
            List<String> lines = new ArrayList<>();
            try {
                Scanner scanner = new Scanner(file);
                while (scanner.hasNextLine()) lines.add(scanner.nextLine());
                scanner.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return lines;
        }

        @Override
        public void run() {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
                System.out.println("Accepted connection");

                dataOutputStream.writeBytes("Accepted connection" + System.lineSeparator());
                dataOutputStream.flush();

                while (connectionNonEnded.get()) {
                    String request = "";
                    try {
                        request = br.readLine();
                    } catch (SocketException e) {
                        System.out.println("Client closed connection!");
                        break;
                    }
                    if (request == "") {
                        break;
                    }
                    switch (request) {
                        case "all files": {
                            StringBuilder stringBuilder = new StringBuilder();
                            String[] txts = new File(".").list((file, s) -> s.endsWith(".txt"));
                            if (txts != null && txts.length > 0) {
                                System.out.println("There are txts");
                                for (String f : txts) {
                                    stringBuilder.append(f);
                                    stringBuilder.append(", ");
                                }
                                System.out.println(stringBuilder.toString());
                                dataOutputStream.writeBytes(stringBuilder.toString() + System.lineSeparator());
                                dataOutputStream.flush();
                            } else {
                                dataOutputStream.writeBytes("No files found!" + System.lineSeparator());
                                dataOutputStream.flush();
                            }
                        }
                        break;
                        case "new file": {
                            try {
                                String fileName = br.readLine();
                                String fileContent = br.readLine();
                                FileWriter fw = new FileWriter(new File(fileName));
                                fw.write(fileContent);
                                fw.close();
                                dataOutputStream.writeBytes("successfully transfered file" +
                                        System.lineSeparator());
                                dataOutputStream.flush();
                            } catch (SocketException e) {
                                System.out.println("Client closed connection!");
                            }
                        }
                        break;
                        case "get file": {
                            try {
                                String fileName = br.readLine();
                                List<String> lines = readFile(fileName);

                                String content = " ";
                                for (String line : lines) {
                                    content = content + line + " ";
                                }

                                dataOutputStream.writeBytes(content + System.lineSeparator());
                                dataOutputStream.flush();
                            } catch (SocketException e) {
                                System.out.println("error");
                            }
                        }
                        break;
                        case "end": {
                            dataOutputStream.close();
                            br.close();
                            this.connection.close();
                            connectionNonEnded.set(false);
                        }
                        break;
                        default: {
                            System.out.println("Bad request " + request);
                            for (char c : request.toCharArray())
                                System.out.println((int) c);
                            dataOutputStream.writeBytes("bad request!" + System.lineSeparator());
                            dataOutputStream.flush();
                        }
                    }
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