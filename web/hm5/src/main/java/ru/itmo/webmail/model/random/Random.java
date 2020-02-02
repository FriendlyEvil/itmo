package ru.itmo.webmail.model.random;

public class Random {
    public static String getRandomString() {
        return java.util.UUID.randomUUID().toString();
    }
}
