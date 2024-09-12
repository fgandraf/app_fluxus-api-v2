package com.felipegandra.app_fluxusapiv2.modules.orders.enums;
import lombok.Getter;

@Getter
public enum Status {
    RECEBIDA(0),
    PENDENTE(1),
    VISTORIADA(2),
    CONCLUIDA(3);

    private final int value;

    Status(int value) {
        this.value = value;
    }

    public static Status fromInt(int value) {
        for (Status status : Status.values()) {
            if (status.getValue() == value) {
                return status;
            }
        }
        throw new IllegalArgumentException("No enum constant with value " + value);
    }
}
