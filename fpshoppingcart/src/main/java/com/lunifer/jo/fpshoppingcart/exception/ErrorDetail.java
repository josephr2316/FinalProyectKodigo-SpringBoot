package com.lunifer.jo.fpshoppingcart.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Date;

@Getter
public class ErrorDetail {

    private Date timestamp;
    private String message;
    private String detail;

    public ErrorDetail() {
        this.timestamp = new Date();
    }

    public ErrorDetail(Date timestamp, String message, String detail) {
        this.timestamp = timestamp;
        this.message = message;
        this.detail = detail;
    }

    public ErrorDetail(String message, String detail) {
        this();
        this.message = message;
        this.detail = detail;
    }
}
