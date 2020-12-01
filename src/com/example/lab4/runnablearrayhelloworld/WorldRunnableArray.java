package com.example.threads.runnablearrayhelloworld;
import java.util.ArrayList;

public class WorldRunnableArray implements Runnable {
    private ArrayList<String> buffer;

    public WorldRunnableArray(ArrayList<String> buffer) {
        this.buffer = buffer;
    }

    @Override
    public void run() {
        synchronized (buffer) {
            try {
                buffer.wait();
                buffer.add("World!\n");
                for(String s : buffer)  System.out.print(s);
            }catch(InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
