package com.galmv.adapter.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {

    private String title;
    private String timestamp;
    private int status;
    private String exception;
    private Map<String, String> details;
}