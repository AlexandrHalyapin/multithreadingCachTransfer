import models.Account;
import models.Transaction;
import org.apache.log4j.Logger;
import services.TransactionManager;
import services.TransactionThread;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Main {
    private static final int NUM_ACCOUNTS = 4;
    private static final int NUM_TRANSACTIONS = 30;
    private static final int NUM_THREADS = 3;
    private static final Logger logger = Logger.getLogger(TransactionThread.class);

    public static void main(String[] args) {

        // Создаем счета
        List<Account> accounts = new ArrayList<>();
        int money = 10000;
        for (int i = 0; i < NUM_ACCOUNTS; i++) {
            Account account = new Account(UUID.randomUUID().toString(), money);
            accounts.add(account);
        }



        TransactionManager transactionManager = new TransactionManager(accounts);
        for (int i = 0; i < NUM_THREADS; i++) {
            Thread thread = new Thread(new TransactionThread(transactionManager));
            logger.info("Запущен поток " + thread.getName());
            thread.start();
        }
    }
}
