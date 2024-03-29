package com.algrince.finaltask.exceptions;

import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * InvalidFormException is raised in controllers when biding result
 * contains errors after validation
 */

@ResponseStatus(HttpStatus.OK)
public class InvalidFormException extends RuntimeException {

    private static List<String> errors;

    public InvalidFormException(BindingResult bindingResult) {
        errors = bindingResult.getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
    }

    public static List<String> getErrors() {
        return errors;
    }
}
