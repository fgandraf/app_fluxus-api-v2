package com.felipegandra.app_fluxusapiv2.modules.orders.enums;

public enum Status {
    RECEBIDA(1),
    PENDENTE(2),
    VISTORIADA(3),
    CONCLUIDA(4);

    private final int value;

    Status(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
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
