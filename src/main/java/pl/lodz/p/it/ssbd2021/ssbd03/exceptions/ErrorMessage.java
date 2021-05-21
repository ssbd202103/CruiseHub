package pl.lodz.p.it.ssbd2021.ssbd03.exceptions;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import java.util.Map;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ErrorMessage {
    private String message;
    private Map<String, String> errors;
}
