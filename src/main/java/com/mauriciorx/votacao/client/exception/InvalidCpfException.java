package com.mauriciorx.votacao.client.exception;

public class InvalidCpfException extends RuntimeException{

    public InvalidCpfException(){
        super("CPF Inválido!");
    }
}
