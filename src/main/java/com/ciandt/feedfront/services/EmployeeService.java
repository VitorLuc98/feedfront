package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.excecoes.*;
import com.ciandt.feedfront.models.Employee;

import java.io.IOException;
import java.util.List;

public class EmployeeService implements Service<Employee> {
    private DAO<Employee> dao;

    public EmployeeService() {
        setDAO(dao);
    }

    @Override
    public List<Employee> listar() throws ArquivoException {
        try {
            return dao.listar();
        } catch (IOException e) {
            throw new ArquivoException("");
        }
    }

    @Override
    public Employee buscar(String id) throws ArquivoException, BusinessException {
        try {
            return dao.buscar(id);
        } catch (IOException e) {
            throw new EntidadeNaoEncontradaException("não foi possível encontrar o employee");
        }
    }

    @Override
    public Employee salvar(Employee employee) throws ArquivoException, BusinessException {
        if (employee == null)
            throw new IllegalArgumentException("employee inválido");
        if (isEmailUnico(listar(), employee)) {
            throw new EmailInvalidoException("E-mail ja cadastrado no repositorio");
        }
        try {
            return dao.salvar(employee);
        } catch (IOException e) {
            throw new BusinessException("");
        }
    }

    @Override
    public Employee atualizar(Employee employee) throws ArquivoException, BusinessException {
        buscar(employee.getId());
        try {
            return salvar(employee);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void apagar(String id) throws ArquivoException, BusinessException {
        try {
            dao.apagar(id);
        } catch (IOException e) {
            throw new EntidadeNaoEncontradaException("Employee não encontrado");
        }
    }

    @Override
    public void setDAO(DAO<Employee> dao) {
        this.dao = dao;
    }

    private boolean isEmailUnico(List<Employee> employees, Employee employeeSalva){
        return employees.stream().anyMatch(x -> !x.getId().equals(employeeSalva.getId()) && x.getEmail().equals(employeeSalva.getEmail()));
    }
}
