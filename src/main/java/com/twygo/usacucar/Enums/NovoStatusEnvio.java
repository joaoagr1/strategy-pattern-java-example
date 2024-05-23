package com.twygo.usacucar.Enums;


public enum NovoStatusEnvio {


    FINALIZADO("F"),


    PENDENTE("P"),


    ERRO("E");

    private final String code;


    NovoStatusEnvio(String code) {
        this.code = code;
    }


    public String getCode() {
        return code;
    }


    public static NovoStatusEnvio fromCode(String code) {
        for (NovoStatusEnvio status : NovoStatusEnvio.values()) {
            if (status.getCode().equalsIgnoreCase(code)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código de status inválido: " + code);
    }

    @Override
    public String toString() {
        return code;
    }
}
