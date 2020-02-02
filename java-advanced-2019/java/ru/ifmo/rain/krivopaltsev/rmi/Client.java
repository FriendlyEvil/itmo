package ru.ifmo.rain.krivopaltsev.rmi;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;

public class Client {
    public static void main(final String... args) throws RemoteException {
        final Bank bank;
        try {
            bank = (Bank) Naming.lookup("//localhost/bank");
        } catch (final NotBoundException e) {
            System.out.println("Bank is not bound");
            return;
        } catch (final MalformedURLException e) {
            System.out.println("Bank URL is invalid");
            return;
        }

        String firstName;
        String secondName;
        String accountId;
        int passportId;
        int change;
        try {
            firstName = args[0];
            secondName = args[1];
            passportId = Integer.parseInt(args[2]);
            accountId = args[3];
            change = Integer.parseInt(args[4]);
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            System.out.println("ERROR: Wrong arguments format. Need <firstName> <secondName> <passportId> <account id> <change>");
            return;
        }

        Person person = bank.getRemotePerson(passportId);
        if (person == null) {
            System.out.println("Creating new person");
            bank.createPerson(passportId, firstName, secondName);
            person = bank.getRemotePerson(passportId);
        }

        if (!bank.checkPerson(passportId, firstName, secondName)) {
            System.out.println("Incorrect person data");
            return;
        }

        if (!bank.getPersonAccounts(person).contains(accountId)) {
            System.out.println("Creating account");
            bank.createAccount(person, accountId);
        }

        Account account = bank.getAccount(person, accountId);
        System.out.println("Account id: " + account.getId());
        System.out.println("Money: " + account.getAmount());
        System.out.println("Adding money");
        account.setAmount(account.getAmount() + change);
        System.out.println("Money: " + account.getAmount());

    }
}
