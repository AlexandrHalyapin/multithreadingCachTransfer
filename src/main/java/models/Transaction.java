package models;

import models.Account;



/**
 * Transaction - класс, представляющий транзакцию между двумя счетами с полями sourceAccount, destinationAccount и amount.
 * */
public class Transaction {
    private Account sender; // счет списания
    private Account receiver; // счет получения
    private int amount; // сумма транзакции

    public Transaction(Account sender, Account receiver, int amount) {
        this.sender = sender;
        this.receiver = receiver;
        this.amount = amount;
    }


    public Account getSender() {
        return sender;
    }

    public Account getReceiver() {
        return receiver;
    }

    public int getAmount() {
        return amount;
    }
}
