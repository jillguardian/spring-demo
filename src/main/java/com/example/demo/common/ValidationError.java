package com.example.demo.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

import java.util.ArrayList;
import java.util.List;

@Value
@Builder
public class ValidationError {

    private String path;
    private String type;
    private String message;

    @Builder.Default
    private List<ValueBoundError> errors = new ArrayList<>();

    @Value
    @AllArgsConstructor(staticName = "of")
    public static class ValueBoundError {

        private String field;
        private String message;

        public static ValueBoundError globalErrorOf(String message) {
            return ValueBoundError.of("*", message);
        }

    }

}
