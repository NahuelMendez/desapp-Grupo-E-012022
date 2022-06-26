package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice;

import ar.edu.unq.desapp.grupoE.backenddesappapi.model.UserException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {UserException.class})
    public ResponseEntity<Object> handlerRegisterUser(UserException error) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ResponseBadRequest response = new ResponseBadRequest( error.getMessage(), badRequest);
        return new ResponseEntity<>(response, badRequest);
    }

    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public ResponseEntity<Object> handlerRequestBody(MethodArgumentNotValidException error) {
        HttpStatus badRequest = HttpStatus.BAD_REQUEST;

        ResponseBadRequest response = new ResponseBadRequest(
                Objects.requireNonNull(error.getFieldError()).getDefaultMessage(),
                badRequest
        );
        return new ResponseEntity<>(response, badRequest);
    }

    @ExceptionHandler(value = {BadCredentialsException.class})
    public ResponseEntity<Object> handlerRequestBody(BadCredentialsException error) {

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(error.getMessage());
    }
}
