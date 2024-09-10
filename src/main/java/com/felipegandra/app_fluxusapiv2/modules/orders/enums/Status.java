package com.felipegandra.app_fluxusapiv2.modules.orders.enums;

import lombok.Getter;

@Getter
public enum Status {
    RECEBIDA("RECEBIDA"),
    PENDENTE("PENDENTE"),
    VISTORIADA("VISTORIADA"),
    CONCLUIDA("CONCLUÍDA");

    private final String status;

    Status(String status) {this.status = status;}
}