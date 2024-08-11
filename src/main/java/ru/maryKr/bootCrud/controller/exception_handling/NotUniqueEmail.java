package ru.maryKr.bootCrud.controller.exception_handling;

public class NotUniqueEmail extends RuntimeException{
    public NotUniqueEmail(String message) {
        super(message);
    }
}
