package controller;

import java.util.concurrent.Semaphore;

public class Threads extends Thread {
    private int threadId;
    private int timer;
    private String tipoPrato;
    private Semaphore semaforoEntrega;

    public Threads(int threadId, Semaphore semaforoEntrega) {
        this.threadId = threadId;
        this.semaforoEntrega = semaforoEntrega;
    }

    public void run() {
        tipoPrato = verifTipoPrato(threadId);
        preparaPrato(tipoPrato);
        
        try {
            semaforoEntrega.acquire();
            entregaPrato(tipoPrato);
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            semaforoEntrega.release();
        }
    }

    public String verifTipoPrato(int id) {
        String tipo = "";
        if (id % 2 == 0) {
            tipo = "lasanha a bolonhesa";
        } else if (id % 2 != 0) {
            tipo = "sopa de cebola";
        }
        System.out.println("Prato #" + id + " sera uma " + tipo + "!");
        return tipo;
    }

    private void preparaPrato(String tipo) {
        System.out.println("Prato #" + threadId + " (" + tipo + ") em estado de preparo");
        if (tipo == "sopa de cebola") {
            timer = (int) (Math.random() * 301) + 500;
        } else if (tipo == "lasanha a bolonhesa") {
            timer = (int) (Math.random() * 601) + 600;
        }
        int porcent = 0;
        while (porcent < 100) {
            System.out.println("Processo do prato #" + threadId + "(" + tipo + ") ==> " + porcent + "%");
            porcent += 100 / (timer / 100);
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Prato #" + threadId + " (" + tipo + ") ficou pronto!");
    }

    public void entregaPrato(String tipo) {
        try {
            System.out.println("Prato #" + threadId + " (" + tipo + ") caminho da entrega!");
            sleep(500);
            System.out.println("Prato #" + threadId + " (" + tipo + ") foi entregue!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}