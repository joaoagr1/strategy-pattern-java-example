package com.twygo.usacucar.Enums;


public enum AcaoHttpEnum {
    SUCCESS(NovoStatusEnvioEnum.FINALIZADO),
    CLIENT_ERROR(NovoStatusEnvioEnum.ERRO),
    SERVER_ERROR(NovoStatusEnvioEnum.ERRO),
    UNKNOWN_ERROR(NovoStatusEnvioEnum.ERRO);

    private final NovoStatusEnvioEnum status;

    AcaoHttpEnum(NovoStatusEnvioEnum status) {
        this.status = status;
    }

    public NovoStatusEnvioEnum getStatus() {
        return status;
    }

    public static AcaoHttpEnum fromStatusCode(int statusCode) {
        return switch (statusCode / 100) {
            case 2 -> SUCCESS;
            case 4 -> CLIENT_ERROR;
            case 5 -> SERVER_ERROR;
            default -> UNKNOWN_ERROR;
        };
    }
}
