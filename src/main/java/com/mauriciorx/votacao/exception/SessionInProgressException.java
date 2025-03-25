package com.mauriciorx.votacao.exception;

public class SessionInProgressException extends RuntimeException{

    public SessionInProgressException(){
        super("Já existe uma sessão Em Andamento!");
    }
}
