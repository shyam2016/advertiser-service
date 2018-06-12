package com.shyam.advertiserservice.exceptions;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ErrorResponse {
    public static final String INTERNAL_ERROR_MESSAGE = "Oops, an error occurred! ";

    private String message;
    private int errorCode;
}
