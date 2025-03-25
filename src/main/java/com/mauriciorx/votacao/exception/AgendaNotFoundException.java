package com.mauriciorx.votacao.exception;

public class AgendaNotFoundException extends RuntimeException{

    public AgendaNotFoundException(Long id){
        super("Não foi encontrada uma pauta com o id: " + id);
    }
}
