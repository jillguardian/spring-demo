package com.example.demo.common;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;

import java.io.*;
import java.util.function.UnaryOperator;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GetResourceContent implements UnaryOperator<String> {

    public static GetResourceContent INSTANCE = new GetResourceContent();

    @Override
    public String apply(String filename) {
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        try (InputStream is = classLoader.getResourceAsStream(filename)) {
            if (is == null) throw new IllegalArgumentException("Can't find resource");
            try (InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader reader = new BufferedReader(isr)) {
                return reader.lines().collect(Collectors.joining(System.lineSeparator()));
            }
        } catch (IOException e) {
            throw new UncheckedIOException("Unable to read content of file", e);
        }
    }

}
