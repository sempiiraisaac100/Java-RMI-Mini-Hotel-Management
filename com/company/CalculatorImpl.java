package com.company;

import java.rmi.RemoteException;

public class CalculatorImpl extends java.rmi.server.UnicastRemoteObject
        implements Calculator {
    public CalculatorImpl()
            throws RemoteException {
        super();
    }

    public long add(long a, long b) throws RemoteException {
        return a + b;
    }

    public long sub(long a, long b) throws RemoteException {
        return a - b;
    }
}