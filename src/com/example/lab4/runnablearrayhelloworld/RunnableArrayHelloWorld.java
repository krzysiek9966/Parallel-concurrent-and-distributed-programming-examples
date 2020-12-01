package com.example.threads.runnablearrayhelloworld;

import java.util.ArrayList;

public class RunnableArrayHelloWorld {
    public static void main(String [] args) {
        final ArrayList<String> buffer = new ArrayList<>();
        HelloRunnableArray helloRunner = new HelloRunnableArray(buffer);
        WorldRunnableArray worldRunner = new WorldRunnableArray(buffer);
        Thread helloThread = new Thread(helloRunner);
        Thread worldThread = new Thread(worldRunner);
        worldThread.start();
        helloThread.start();
//        helloRunner.run();
//        worldRunner.run();
    }
}
