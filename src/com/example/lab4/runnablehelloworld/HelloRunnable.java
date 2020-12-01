package com.example.threads.runnablehelloworld;

public class HelloRunnable implements Runnable {
    private Boolean waitForHello;
    public HelloRunnable(Boolean waitForHello) {
        this.waitForHello = waitForHello;
    }
    @Override
    public void run() {
        synchronized (waitForHello) {
            System.out.print("Hello");
            waitForHello.notify();
        }
    }
}
