package com.ciandt.feedfront.controllers.exceptions;

import com.ciandt.feedfront.exceptions.EmailInvalidoException;
import com.ciandt.feedfront.exceptions.EntidadeNaoEncontradaException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

@ControllerAdvice
public class ManipuladorExcecoesResource {

    @ExceptionHandler(EntidadeNaoEncontradaException.class)
    public ResponseEntity<ErroPadrao> entidadeNaoEncontrada(EntidadeNaoEncontradaException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.NOT_FOUND;
        ErroPadrao error = new ErroPadrao(LocalDateTime.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

//    @ExceptionHandler(EmailInvalidoException.class)
//    public ResponseEntity<ErroPadrao> emailInvalido(EmailInvalidoException e, HttpServletRequest request){
//        HttpStatus status = HttpStatus.BAD_REQUEST;
//        ErroPadrao error = new ErroPadrao(LocalDateTime.now(), status.value(), e.getMessage(), request.getRequestURI());
//        return ResponseEntity.status(status).body(error);
//    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErroPadrao> emailInvalido(DataIntegrityViolationException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroPadrao error = new ErroPadrao(LocalDateTime.now(), status.value(), "já existe um employee cadastrado com esse e-mail", request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErroPadrao> argumentoInvalido(IllegalArgumentException e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;
        ErroPadrao error = new ErroPadrao(LocalDateTime.now(), status.value(), e.getMessage(), request.getRequestURI());
        return ResponseEntity.status(status).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErroValidacao> validacoesEntrada(MethodArgumentNotValidException  e, HttpServletRequest request){
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ErroValidacao error = new ErroValidacao();
        error.setErro("Erro no preenchimento dos dados !");
        error.setDataDoErro(LocalDateTime.now());
        error.setStatus(status.value());
        error.setCaminho(request.getRequestURI());

        for (FieldError f: e.getBindingResult().getFieldErrors()) {
            error.addErro(f.getField(), f.getDefaultMessage());
        }
        return ResponseEntity.status(status).body(error);
    }
}
