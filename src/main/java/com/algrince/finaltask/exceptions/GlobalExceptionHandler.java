package com.algrince.finaltask.exceptions;

import com.algrince.finaltask.dto.ErrorMessageDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.nio.file.AccessDeniedException;
import java.util.List;

/**
 * Handles ResourceNotFoundException, BadCredentialsException,
 * AccessDeniedException, InvalidFormException and RunTimeException
 */

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorMessageDTO> handleResourceNotFoundException(
            ResourceNotFoundException e) {

        log.warn("Client tries no access resource that does not exist");

        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();

        errorMessageDTO.setStatus(HttpStatus.NOT_FOUND);
        errorMessageDTO.setMessage(e.getLocalizedMessage());

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessageDTO);

    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    public ResponseEntity<ErrorMessageDTO> handleBadCredentialsException(
            BadCredentialsException e) {

        log.info("Incorrect credentials given by the user");
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();

        errorMessageDTO.setStatus(HttpStatus.UNAUTHORIZED);
        errorMessageDTO.setMessage("Incorrect credentials");

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorMessageDTO);
    }

    @ExceptionHandler(RuntimeException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorMessageDTO> handleRunTimeException(
            RuntimeException e) {

        log.warn(e.getLocalizedMessage());
        ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();

        errorMessageDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        errorMessageDTO.setMessage("There was an error while processing your request. Is the problem persists, please contact admin.");

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorMessageDTO);
    }

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorMessageDTO> handleAccessDeniedException(
            AccessDeniedException e) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
         if (auth != null) {
             log.warn ("User: " + auth.getName() +  "attempted to access the protected URL");
         }

         ErrorMessageDTO errorMessageDTO = new ErrorMessageDTO();
         errorMessageDTO.setStatus(HttpStatus.FORBIDDEN);
         errorMessageDTO.setMessage(e.getLocalizedMessage());

         return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorMessageDTO);
    }

    @ExceptionHandler(InvalidFormException.class)
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<Object> handleInvalidFormException(
            InvalidFormException e) {

        List<String> errors = InvalidFormException.getErrors();
        return ResponseEntity.status(HttpStatus.OK).body(errors);
    }
}
