package com.mauriciorx.votacao.exception;

public class AlreadyVotedException extends RuntimeException {

    public AlreadyVotedException() {
        super("O Associado já votou nesta Pauta!");
    }
}