package com.mauriciorx.votacao.exception;

public class AssociateNotFoundException extends RuntimeException{

    public AssociateNotFoundException(Long id){
        super("Não foi encontrado um associado com o id: " + id);
    }

    public AssociateNotFoundException(String message){
        super(message);
    }
}
