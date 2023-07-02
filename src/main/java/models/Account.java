package models;

import org.apache.log4j.Logger;
import services.TransactionManager;

/**
 * Account - класс, представляющий сущность банковского счета с полями ID и Money.
 * В этом классе реализованы методы для зачисления и списания денег.
 * */
public class Account {
    private String id;
    private int money;



    public Account(String id, int money) {
        this.id = id;
        this.money = money;
    }


    public synchronized void deposit(int amount) { // Метод синхронизирован для предотвращения аномалий многопоточности
        if (amount > 0) {
            money += amount;
        }
    }

    public synchronized void withdraw(int amount) { // Метод синхронизирован для предотвращения аномалий многопоточности
        if (money >= amount && amount > 0) {
            money -= amount;
        }
    }

    public String getId() {
        return id;
    }

    public int getMoney() {
        return money;
    }

}
