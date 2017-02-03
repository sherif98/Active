package com.edu.active.controllers.exceptions;

import com.edu.active.controllers.dto.Error;
import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandlingController {

    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Error handleResourceNotFound(ResourceNotFoundException exc) {
        return new Error("[ " + exc.getResourceName() + " ] Not Found");
    }

    @ExceptionHandler(ResourceAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Error handleResourceAlreadyExists(ResourceAlreadyExistsException exc) {
        return new Error("[ " + exc.getResourceName() + " ] Already Exists");
    }

    @ExceptionHandler(InvalidDataException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Error handleInvalidDataException(InvalidDataException exc) {
        return new Error(exc.getMessage());
    }

    public static void userNotFound(long userId) {
        throw new ResourceNotFoundException("user " + userId);
    }

    public static void categoryNotFound(long categoryId) {
        throw new ResourceNotFoundException("category  " + categoryId);
    }

    public static void postNotFound(long postId) {
        throw new ResourceNotFoundException("post  " + postId);
    }

    public static void invalidData(List<ObjectError> allErrors) {
        throw new InvalidDataException(allErrors);
    }
}
