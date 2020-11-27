package com.teste.DesafioBento.error;

public class RepeatedDataException extends RuntimeException {
    public RepeatedDataException() {
        super();
    }

    public RepeatedDataException(String message) {
        super(message);
    }
}
