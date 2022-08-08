package com.ciandt.feedfront.services;

import com.ciandt.feedfront.exceptions.BusinessException;
import com.ciandt.feedfront.exceptions.EmailInvalidoException;
import com.ciandt.feedfront.exceptions.EntidadeNaoEncontradaException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.repositories.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.persistence.PersistenceException;
import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Predicate;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    @Override
    public List<Employee> listar() {
        return employeeRepository.findAll();
    }

    @Override
    public Employee buscar(long id) throws BusinessException {
        return employeeRepository.findById(id).orElseThrow(() -> new EntidadeNaoEncontradaException("não foi possível encontrar o employee"));
    }

    @Override
    public Employee salvar(Employee employee) throws BusinessException {
        if (employeeRepository.existsByEmail(employee.getEmail())){
            throw new EmailInvalidoException("já existe um employee cadastrado com esse e-mail");
        }
        employee = employeeRepository.save(employee);
        return employee;
    }

    @Override
    public Employee atualizar(Employee employee) throws BusinessException {
        if (employee == null){
            throw new IllegalArgumentException();
        }
        if (employee.getId() == null){
            throw new IllegalArgumentException("employee inválido: não possui ID");
        }
        if (employeeRepository.existsByEmail(employee.getEmail())){
            throw new EmailInvalidoException("já existe um employee cadastrado com esse e-mail");
        }
        buscar(employee.getId());
        employee = employeeRepository.save(employee);
        return employee;
    }

    @Override
    public void apagar(long id) throws BusinessException {
        buscar(id);
        employeeRepository.deleteById(id);
    }
}