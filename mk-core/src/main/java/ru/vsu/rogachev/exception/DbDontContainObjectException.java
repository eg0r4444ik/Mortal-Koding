package ru.vsu.rogachev.exception;

public class DbDontContainObjectException extends Exception{

    public DbDontContainObjectException() {
    }

    public DbDontContainObjectException(String message) {
        super(message);
    }
}
