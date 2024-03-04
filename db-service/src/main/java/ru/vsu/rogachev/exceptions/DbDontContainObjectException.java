package ru.vsu.rogachev.exceptions;

public class DbDontContainObjectException extends Exception{

    public DbDontContainObjectException() {
    }

    public DbDontContainObjectException(String message) {
        super(message);
    }
}
