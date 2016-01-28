package com.example.errors;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.UNAUTHORIZED, reason="Invalid credentials")
public class InvalidCredentialsException extends RuntimeException {

}