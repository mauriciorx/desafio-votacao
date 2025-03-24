package com.mauriciorx.votacao.exception;

public class ExistentCpfException extends RuntimeException{

    public ExistentCpfException(String message) {
        super("Já existe um cadastro para o cpf: " + message);
    }
}
