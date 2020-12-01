package com.example.threads.runnablearrayhelloworld;
import java.util.ArrayList;

public class HelloRunnableArray implements Runnable {
    private ArrayList<String> buffer;

    public HelloRunnableArray(ArrayList<String> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        synchronized (buffer) {
            buffer.add("Hello, ");
            buffer.notify();
        }
    }
}
