package ru.troyan.cityinfoapi.exception;

public class InvalidCoordinateValueException extends Exception{
    public InvalidCoordinateValueException(String message) {
        super(message);
    }
}
