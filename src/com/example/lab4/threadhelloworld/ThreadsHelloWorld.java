package com.example.threads.threadhelloworld;

public class ThreadsHelloWorld {
    public static void main(String [] args) {
        HelloThread helloThread = new HelloThread(Boolean.FALSE);
        WorldThread worldThread = new WorldThread(Boolean.FALSE);
        worldThread.start();
        helloThread.start();
    }
}
