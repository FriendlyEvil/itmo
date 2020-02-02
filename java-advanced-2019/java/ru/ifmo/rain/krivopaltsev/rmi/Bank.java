package ru.ifmo.rain.krivopaltsev.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Set;

public interface Bank extends Remote {
    boolean createPerson(int passportId, String name, String surname) throws RemoteException;

    boolean checkPerson(int passportId, String firstName, String lastName) throws RemoteException;

    Person getLocalPerson(int passportId) throws RemoteException;

    Person getRemotePerson(int passportId) throws RemoteException;

    Set<String> getPersonAccounts(Person person) throws RemoteException;

    boolean createAccount(Person person, String id) throws RemoteException;

    Account getAccount(Person person, String id) throws RemoteException;
}
