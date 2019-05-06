package ru.ifmo.rain.krivopaltsev.rmi;

import java.util.concurrent.atomic.AtomicInteger;

public class RemoteAccount implements Account {
    private final String id;
    private final AtomicInteger amount;

    public RemoteAccount(final String id) {
        this.id = id;
        amount = new AtomicInteger(0);
    }

    public String getId() {
        return id;
    }

    public int getAmount() {
        System.out.println("Getting amount of money for account " + id);
        return amount.get();
    }

    public void setAmount(final int amount) {
        System.out.println("Setting amount of money for account " + id);
        this.amount.set(amount);
    }
}
