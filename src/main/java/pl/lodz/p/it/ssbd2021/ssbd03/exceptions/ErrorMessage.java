package pl.lodz.p.it.ssbd2021.ssbd03.exceptions;

import lombok.RequiredArgsConstructor;

import java.util.Map;

@RequiredArgsConstructor
public class ErrorMessage {
    private final String message;
    private final Map<String, String> errors;
}
