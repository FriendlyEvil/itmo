package ru.ifmo.rain.krivopaltsev.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListSet;

public class RemoteBank implements Bank, Remote {
    private final int port;
    private final ConcurrentMap<String, Account> accounts = new ConcurrentHashMap<>();
    private final ConcurrentMap<Integer, Person> persons = new ConcurrentHashMap<>();
    private final ConcurrentMap<Integer, Set<String>> accountByPassport = new ConcurrentHashMap<>();

    public RemoteBank(final int port) {
        this.port = port;
    }

    @Override
    public boolean createPerson(int passportId, String firstName, String lastName) throws RemoteException {
        if (firstName == null || lastName == null || passportId < 0 || persons.get(passportId) != null) {
            return false;
        }
        System.out.println("Creating person " + firstName + " " + lastName + " with passport " + passportId);

        Person person = new RemotePerson(passportId, firstName, lastName);
        persons.put(passportId, person);
        accountByPassport.put(passportId, new ConcurrentSkipListSet<>());
        UnicastRemoteObject.exportObject(person, port);
        return true;
    }

    @Override
    public boolean checkPerson(int passportId, String firstName, String lastName) throws RemoteException {
        if (firstName == null || lastName == null || passportId < 0) {
            return false;
        }
        System.out.println("Checking person '" + firstName + " " + lastName + " with passport " + passportId + "'");
        Person person = persons.get(passportId);
        return person != null && person.getFirstName().equals(firstName) && person.getLastName().equals(lastName);
    }

    @Override
    public Person getLocalPerson(int passportId) throws RemoteException {
        if (passportId < 0) {
            return null;
        }

        Person person = persons.get(passportId);
        if (person == null) {
            return null;
        }
        System.out.println("Getting local person by passport " + passportId);
        Set<String> accountNames = getPersonAccounts(person);
        Map<String, LocalAccount> accounts = new ConcurrentHashMap<>();
        accountNames.forEach(x -> {
            try {
                Account curRemote = getAccount(person, x);
                accounts.put(x, new LocalAccount(curRemote.getId(), curRemote.getAmount()));
            } catch (RemoteException e) {
                System.out.println("Error while creating local accounts");
            }
        });
        return new LocalPerson(person.getPassportId(), person.getFirstName(), person.getLastName(), accounts);
    }

    @Override
    public Person getRemotePerson(int passportId) throws RemoteException {
        if (passportId < 0) {
            return null;
        }
        System.out.println("Getting remote person by passport " + passportId);
        return persons.get(passportId);
    }

    @Override
    public Set<String> getPersonAccounts(Person person) throws RemoteException {
        if (person == null) {
            return null;
        }
        System.out.println("Getting accounts by passport " + person.getPassportId());
        if (person instanceof LocalPerson)
            return ((LocalPerson) person).getAccounts();
        return accountByPassport.get(person.getPassportId());
    }

    @Override
    public boolean createAccount(Person person, String id) throws RemoteException {
        if (person == null || id == null) {
            return false;
        }

        String accountId = person.getPassportId() + ":" + id;
        if (accounts.containsKey(accountId)) {
            return false;
        }

        System.out.println("Creating account " + accountId);
        final Account account = new RemoteAccount(id);
        accounts.put(accountId, account);
        UnicastRemoteObject.exportObject(account, port);
        accountByPassport.get(person.getPassportId()).add(id);
        if (person instanceof LocalPerson) {
            ((LocalPerson) person).addAccount(id, new LocalAccount(id));
        }

        return true;
    }

    @Override
    public Account getAccount(Person person, String id) throws RemoteException {
        if (person == null || id == null) {
            return null;
        }

        String accountId = person.getPassportId() + ":" + id;
        Account account = accounts.get(accountId);

        if (account == null) {
            return null;
        }

        System.out.println("Getting account " + accountId);

        if (person instanceof LocalPerson) {
            return ((LocalPerson) person).getAccount(id);
        }
        return account;
    }
}
