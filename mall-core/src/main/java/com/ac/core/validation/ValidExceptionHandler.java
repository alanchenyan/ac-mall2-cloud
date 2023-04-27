package com.ac.core.validation;

import cn.hutool.core.collection.CollectionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
@Slf4j
public class ValidExceptionHandler {

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public Object errorHandler(HttpServletRequest request, MethodArgumentNotValidException e) {
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        if (CollectionUtil.isEmpty(errors)) {
            return errors;
        }
        String message = errors.stream().map(ObjectError::getDefaultMessage).collect(Collectors.joining(";"));
        return message;
    }
}
