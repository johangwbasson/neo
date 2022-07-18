package net.johanbasson.neo.common;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class ApiError extends RuntimeException {

    public ApiError(String message) {
        super(message);
    }

}
