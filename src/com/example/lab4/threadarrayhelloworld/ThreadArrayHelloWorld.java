package com.example.threads.threadarrayhelloworld;
import java.util.ArrayList;

public class ThreadArrayHelloWorld {
    public static void main(String [] args) {
        final ArrayList<String> buffer = new ArrayList<>();
        HelloThreadArray helloThread = new HelloThreadArray(buffer);
        WorldThreadArray worldThread = new WorldThreadArray(buffer);
        worldThread.start();
        helloThread.start();
    }
}
