package com.vetnova.ms_duenos.exception;

public class DuenoNoEncontradoException extends RuntimeException {
    public DuenoNoEncontradoException(String mensaje) {
        super(mensaje);
    }
}