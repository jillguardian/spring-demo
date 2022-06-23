package com.example.demo.common;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class Error {

    private String path;
    private String type;
    private String message;
    private String cause;

}
