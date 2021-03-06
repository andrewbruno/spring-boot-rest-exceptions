package io.tpd.superheroes.controller;

import io.tpd.superheroes.controller.errors.SuperHeroAppError;
import io.tpd.superheroes.exceptions.NonAllowedHeroException;
import io.tpd.superheroes.exceptions.NonExistingHeroException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * Maps exceptions to HTTP responses
 *
 * @author moises.macero
 */
@ConditionalOnProperty(name = "superheroes.errors.controlleradvice", havingValue = "true")
@RestControllerAdvice
public class SuperHeroControllerAdvice {

    @Value("${superheroes.sendreport.uri}")
    private String sendReportUri;
    @Value("${superheroes.api.version}")
    private String currentApiVersion;

    @ExceptionHandler(NonExistingHeroException.class)
    public ResponseEntity<SuperHeroAppError> handleNonExistingHero(HttpServletRequest request,
                                                                   NonExistingHeroException ex) {
        final SuperHeroAppError error = new SuperHeroAppError(
                currentApiVersion,
                ex.getErrorCode(),
                "This superhero is hiding in the cave",
                "superhero-exceptions",
                ex.getMessage(),
                "Saving someone",
                sendReportUri
        );
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND); // 404
    }

    @ExceptionHandler(NonAllowedHeroException.class)
    public ResponseEntity<SuperHeroAppError> handleNonExistingHero(HttpServletRequest request,
                                                                   NonAllowedHeroException ex) {
        final SuperHeroAppError error = new SuperHeroAppError(
                currentApiVersion,
                ex.getErrorCode(),

                "This superhero operation is not allowed",
                "superhero-exceptions",
                ex.getMessage(),
                "Saving someone",
                sendReportUri
        );
        return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED); // 405
    }

}
