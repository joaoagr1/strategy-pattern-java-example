package com.twygo.usacucar.Enums;


public enum NovoStatusEnvioEnum {


    FINALIZADO("F"),


    PENDENTE("P"),


    ERRO("E");

    private final String code;


    NovoStatusEnvioEnum(String code) {
        this.code = code;
    }


    public String getCode() {
        return code;
    }


    public static NovoStatusEnvioEnum fromCode(String code) {
        for (NovoStatusEnvioEnum status : NovoStatusEnvioEnum.values()) {
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
