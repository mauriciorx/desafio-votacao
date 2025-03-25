package com.mauriciorx.votacao.exception;

public class VoteNotFoundException extends RuntimeException {

    public VoteNotFoundException(Long id) {
        super("Não foi encontrado um voto com o id: " + id);
    }
}