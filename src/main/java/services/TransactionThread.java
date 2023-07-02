package services;

import org.apache.log4j.Logger;

import java.util.concurrent.ThreadLocalRandom;

public class TransactionThread implements Runnable {
    private TransactionManager transactionManager;

    public TransactionThread(TransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            transactionManager.executeTransaction();
            try {
                Thread.sleep(ThreadLocalRandom.current().nextLong(1000, 2000));
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
