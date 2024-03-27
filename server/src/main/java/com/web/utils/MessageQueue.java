package com.web.utils;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MessageQueue {

    private static final BlockingQueue<Object> queue = new ArrayBlockingQueue<>(1000);;



    public static boolean enqueue(Object message) {
        return queue.offer(message);
    }

    public static Object dequeue() throws InterruptedException {
        return queue.take();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }

    public int size() {
        return queue.size();
    }
}
