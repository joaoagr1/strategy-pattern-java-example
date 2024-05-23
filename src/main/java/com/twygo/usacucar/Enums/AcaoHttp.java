package com.twygo.usacucar.Enums;

import com.twygo.usacucar.Enums.NovoStatusEnvio;


public enum AcaoHttp {
    SUCCESS(NovoStatusEnvio.FINALIZADO),
    CLIENT_ERROR(NovoStatusEnvio.ERRO),
    SERVER_ERROR(NovoStatusEnvio.ERRO),
    UNKNOWN_ERROR(NovoStatusEnvio.ERRO);

    private final NovoStatusEnvio status;

    AcaoHttp(NovoStatusEnvio status) {
        this.status = status;
    }

    public NovoStatusEnvio getStatus() {
        return status;
    }

    public static AcaoHttp fromStatusCode(int statusCode) {
        if (statusCode >= 200 && statusCode < 300) {
            return SUCCESS;
        } else if (statusCode >= 400 && statusCode < 500) {
            return CLIENT_ERROR;
        } else if (statusCode >= 500 && statusCode < 600) {
            return SERVER_ERROR;
        } else {
            return UNKNOWN_ERROR;
        }
    }
}
