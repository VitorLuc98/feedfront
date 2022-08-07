package com.ciandt.feedfront.controllers;


import com.ciandt.feedfront.exceptions.BusinessException;
import com.ciandt.feedfront.models.Feedback;
import com.ciandt.feedfront.services.FeedbackService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/v1/feedbacks")
@RequiredArgsConstructor
public class FeedbackController {
    private final FeedbackService feedbackService;


    @ApiOperation(value = "Este endpoint retorna todos os feedbacks cadastrados na base de dados.")
    @GetMapping
    public ResponseEntity<List<Feedback>> listar() {
        return ResponseEntity.ok(feedbackService.listar());
    }
    @ApiOperation(value = "Este endpoint retorna o feedback por id")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Feedback> buscar(@PathVariable  long id) throws BusinessException {
        return ResponseEntity.ok(feedbackService.buscar(id));
    }
    @ApiOperation(value = "Este endpoint salva um no feedbakc na base de dados")
    @PostMapping
    public ResponseEntity<Feedback> salvar(@RequestBody Feedback feedback) throws BusinessException {
        return ResponseEntity.ok(feedbackService.salvar(feedback));
    }
}
