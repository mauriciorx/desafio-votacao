package com.mauriciorx.votacao.api.v1.enums;

public enum VoteEnum {
    YES("Sim"),
    NO("Não");

    private final String valor;

    VoteEnum( String voto ) {
        this.valor = voto;
    }

    public String getValue() {
        return this.valor;
    }
}
