package ar.edu.unq.desapp.grupoE.backenddesappapi.webservice;

import org.springframework.http.HttpStatus;

public class ResponseBadRequest {

    private String message;
    private final Integer status;
    private HttpStatus error;

    public ResponseBadRequest(String message, HttpStatus badRequest) {
        this.message = message;
        this.status = 400;
        this.error = badRequest;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public HttpStatus getError() {
        return error;
    }

    public void setError(HttpStatus error) {
        this.error = error;
    }

    public Integer getStatus() {
        return status;
    }
}
