package com.dora.weblab4.controller.validation;

import com.dora.weblab4.service.NoSuchUserException;
import com.dora.weblab4.service.PasswordCheckException;
import com.dora.weblab4.service.UserExistsException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
@EnableWebMvc
public class RestExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleNotValid(MethodArgumentNotValidException exception) {
        List<ErrorReason> reasons = exception.getFieldErrors().stream()
                .map(err -> new ErrorReason(err.getField(), err.getDefaultMessage()))
                .collect(Collectors.toList());
        return new ErrorMessage("bad_field_values", reasons);
    }

    @ExceptionHandler(MismatchedInputException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleWrongTypes(MismatchedInputException e) {
        List<ErrorReason> reasons = new ArrayList<>();
        List<JsonMappingException.Reference> path = e.getPath();
        String field = path.get(path.size() - 1).getFieldName();
        String targetType = e.getTargetType().getSimpleName().toLowerCase();
        reasons.add(new ErrorReason(field, "must_be_" + targetType));
        return new ErrorMessage("bad_field_types", reasons);
    }

    @ExceptionHandler(NoSuchUserException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage handleNoSuchUser(NoSuchUserException e) {
        List<ErrorReason> reasons = new ArrayList<>();

        return new ErrorMessage("no_such_user", reasons);
    }

    @ExceptionHandler(PasswordCheckException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) 
    public ErrorMessage handlePasswordCheck(PasswordCheckException e) {
        List<ErrorReason> reasons = new ArrayList<>();

        return new ErrorMessage("password_check", reasons);
    }

    @ExceptionHandler(UserExistsException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST) 
    public ErrorMessage handleUserExists(UserExistsException e) {
        List<ErrorReason> reasons = new ArrayList<>();

        return new ErrorMessage("user_exists", reasons);
    }
}
