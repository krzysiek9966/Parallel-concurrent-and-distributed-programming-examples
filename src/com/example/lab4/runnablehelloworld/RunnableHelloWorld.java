package com.example.threads.runnablehelloworld;

public class RunnableHelloWorld {
    public static void main(String [] args) {
        HelloRunnable helloRunner = new HelloRunnable(Boolean.FALSE);
        WorldRunnable worldRunner = new WorldRunnable(Boolean.FALSE);
        Thread helloThread = new Thread(helloRunner);
        Thread worldThread = new Thread(worldRunner);
        worldThread.start();
        helloThread.start();
    }
}
