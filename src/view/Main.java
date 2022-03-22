package view;

import java.util.concurrent.Semaphore;

import controller.Threads;

public class Main {
    public static void main(String[] args) {
        int permits=1;
        Semaphore semaforo = new Semaphore(permits);
        for (int i = 1; i < 6; i++) {
            Thread pratos = new Threads(i, semaforo);
            pratos.start();
        }
    }
}