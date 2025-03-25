package com.mauriciorx.votacao.exception;

public class SessionClosedException extends RuntimeException{

    public SessionClosedException(){
        super("Sessão Encerrada!");
    }
}
