package com.example.lab6.producerconsumerexactlyruns;

import java.util.LinkedList;
import java.util.Random;

public class ProducerConsumerExactlyRuns {
    static LinkedList<String> buffer = new LinkedList<>();
    static final int BUFFER_SIZE = 10;
    public static void main(String[] args) {

        class Producer extends Thread {
            private String name;
            private int numberElements;
            public Producer(String name, int numberElements) {
                this.name = name;
                this.numberElements = numberElements;
            }
            @Override
            public void run() {
                while (this.numberElements != 0) {
                    synchronized (buffer) {
                        if (buffer.size() == BUFFER_SIZE) {
                            try {
                                buffer.wait();
                                System.out.println("Producer " + name + " is waiting, full buffer");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            Random r = new Random();
                            String[] words = {"aaa", "bbb", "ccc", "ddd", "eee", "fff"};
                            String s = words[r.nextInt(words.length)];
                            System.out.println("Producer " + name + " produces: " + s);
                            buffer.addLast(s);
                            buffer.notify();
                            this.numberElements--;
                        }
                        try {
                            Thread.sleep(10);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
        class Consumer extends Thread {
            private String name;
            private int numberElements;
            public Consumer(String name, int numberElements) {
                this.name = name;
                this.numberElements = numberElements;
            }
            @Override
            public void run() {
                while (this.numberElements != 0) {
                    synchronized (buffer) {
                        if (buffer.size() == 0) {
                            try {
                                buffer.wait();
                                System.out.println("Consumer " + name + " is waiting, empty buffer");
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        } else {
                            String elem = buffer.getFirst();
                            System.out.println(this.getPriority());
                            System.out.println("Consumer" + name + " consumes: " + elem + " and process it to: "
                                    + elem.toUpperCase());
                            this.numberElements--;
                            try {
                                Thread.sleep(10);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            buffer.removeFirst();
                            buffer.notify();
                        }
                    }
                }
            }
        }
        new Producer("I", 1).start();
        new Producer("II",3).start();
        new Consumer("I", 2).start();
        new Consumer("II",2).start();
    }
}