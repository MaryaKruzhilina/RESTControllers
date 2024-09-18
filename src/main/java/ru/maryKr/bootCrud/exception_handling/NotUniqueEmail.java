package ru.maryKr.bootCrud.exception_handling;

public class NotUniqueEmail extends RuntimeException{
    public NotUniqueEmail(String message) {
        super(message);
    }
}
