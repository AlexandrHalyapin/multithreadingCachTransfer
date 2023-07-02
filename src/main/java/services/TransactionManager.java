package services;

import models.Account;
import models.Transaction;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class TransactionManager {
    private static final Logger logger = Logger.getLogger(TransactionManager.class);

    private final List<Account> accounts;
    private final Random random;
    private static final int MAX_TRANSACTIONS = 30;
    private final AtomicInteger transactionCount = new AtomicInteger(0);
    int totalMoney;
    public TransactionManager(List<Account> accounts) {
        totalMoney = accounts.stream().mapToInt(Account::getMoney).sum();
        logger.info("Инициализирован менеджер транзакций, сумма средств на всех счетах: " + totalMoney);
        this.accounts = accounts;
        this.random = new Random();
    }

    public synchronized void executeTransaction() {
        // Выбираем случайные счета для транзакции
        Account sender = getRandomAccount();
        Account receiver = getRandomAccount();

        // Генерируем случайную сумму для транзакции
        int amount = random.nextInt(1000);

        // Создаем объект транзакции
        Transaction transaction = new Transaction(sender, receiver, amount);

        // Проверяем достаточно ли средств на счете отправителя
        if (sender.getMoney() >= amount) {
            // Выполняем перевод средств
            sender.withdraw(amount);
            receiver.deposit(amount);

            // Записываем результат транзакции в лог
            String message = String.format("Выполнена транзакция: Счет отправителя: %s, Счет получателя: %s, Сумма: %d",
                    sender.getId(), receiver.getId(), amount);
            logger.info(message);


            int count = transactionCount.incrementAndGet();
            System.out.println(count + " транзакций уже выполнено");
            if (count >= MAX_TRANSACTIONS) {
                totalMoney = accounts.stream().mapToInt(Account::getMoney).sum();
                Thread.currentThread().getThreadGroup().interrupt();
                logger.info("Все транзакции выполнены, сумма средств на всех счетах: " + totalMoney);
            }

        } else {
            // Записываем ошибку в лог, если недостаточно средств на счете отправителя
            String errorMessage = String.format("Ошибка транзакции: Недостаточно средств на счете отправителя: %s", sender.getId());
            logger.error(errorMessage);
        }
    }

    private Account getRandomAccount() {
        // Генерируем случайный индекс для выбора счета из списка счетов
        int index = random.nextInt(accounts.size());
        return accounts.get(index);
    }
}
