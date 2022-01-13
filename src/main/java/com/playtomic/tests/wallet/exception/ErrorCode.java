package com.playtomic.tests.wallet.exception;

import lombok.AllArgsConstructor;
import lombok.NonNull;

@AllArgsConstructor
public enum ErrorCode {

    INTERNAL_ERROR("001-001", "An internal error occurs."),
    WALLET_NOT_FOUND("001-002", "Wallet with uuid %s not found."),
    AMOUNT_TOO_SMALL("001-003", "The introduced amount is too small");

    private final @NonNull String code;
    private final @NonNull String message;

    public String getMessage() {
        return this.message;
    }

    public String getCode() {
        return this.code;
    }

}