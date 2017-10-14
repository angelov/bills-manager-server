package me.angelovdejan.billsmanager.api.exceptionHandlers;

import me.angelovdejan.billsmanager.api.responses.NotFoundResponse;
import me.angelovdejan.billsmanager.api.responses.Response;
import me.angelovdejan.billsmanager.core.exceptions.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ResourceNotFoundExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    protected Response handle(ResourceNotFoundException ex) {
        return new NotFoundResponse(ex.getMessage());
    }

}
