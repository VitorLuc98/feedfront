package com.ciandt.feedfront.controllers;

import com.ciandt.feedfront.exceptions.BusinessException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.services.EmployeeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    @ApiOperation(value = "Este endpoint retorna todes os Employees cadastrados na base de dados.")
    @GetMapping
    public ResponseEntity<List<Employee>> listar()  {
        return ResponseEntity.ok(employeeService.listar());
    }

    @ApiOperation(value = "Este endpoint retorna o Employee por id.")
    @GetMapping(value = "/{id}")
    public ResponseEntity<Employee> buscar(@PathVariable long id) throws BusinessException {
        return ResponseEntity.ok(employeeService.buscar(id));
    }

    @ApiOperation(value = "Este endpoint salva um novo Employee na base de dados.")
    @PostMapping
    public ResponseEntity<Employee> salvar(@Valid @RequestBody Employee employee) throws BusinessException {
//        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(employee.getId()).toUri();
        return ResponseEntity.ok(employeeService.salvar(employee));
    }

    @ApiOperation(value = "Este endpoint remove um Employee pelo id.")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity apagar(@PathVariable long id) throws BusinessException {
        employeeService.apagar(id);
        return ResponseEntity.noContent().build();
    }

    @ApiOperation(value = "Este endpoint atualiza os dados de um Employee.")
    @PutMapping
    public ResponseEntity<Employee> atualizar (@Valid @RequestBody Employee employee) throws BusinessException {
        return ResponseEntity.ok(employeeService.atualizar(employee));
    }
}