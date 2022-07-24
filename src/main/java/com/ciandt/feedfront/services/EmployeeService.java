package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;
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
            throw new BusinessException("Employee não encontrado");
        }
    }

    @Override
    public Employee salvar(Employee employee) throws ArquivoException, BusinessException {
        try {
            return dao.salvar(employee);
        } catch (IOException e) {
            throw new BusinessException("");
        }
    }

    @Override
    public Employee atualizar(Employee employee) throws ArquivoException, BusinessException {
        Employee employee1 = buscar(employee.getId());
        try {
            return dao.salvar(employee1);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void apagar(String id) throws ArquivoException, BusinessException {
        try {
            dao.apagar(id);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void setDAO(DAO<Employee> dao) {
        this.dao = dao;
    }
}
