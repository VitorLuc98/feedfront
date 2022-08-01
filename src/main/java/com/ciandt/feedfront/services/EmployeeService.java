package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.daos.EmployeeDAO;
import com.ciandt.feedfront.excecoes.EntidadeNaoEncontradaException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.excecoes.BusinessException;

import java.util.List;

public class EmployeeService implements Service<Employee> {
    private DAO<Employee> dao;

    public EmployeeService() {
        setDAO(new EmployeeDAO());
    }

    @Override
    public List<Employee> listar() {
        return dao.listar();
    }

    @Override
    public Employee buscar(long id) throws BusinessException {
        return dao.buscar(id).orElseThrow(() -> new EntidadeNaoEncontradaException("não foi possível encontrar o employee"));
    }

    @Override
    public Employee salvar(Employee employee) throws BusinessException {
        return dao.salvar(employee);
    }

    @Override
    public Employee atualizar(Employee employee) throws BusinessException {
        Employee employeeSalvo = buscar(employee.getId());
        employeeSalvo.setNome(employee.getNome());
        employeeSalvo.setSobrenome(employee.getSobrenome());
        employeeSalvo.setEmail(employee.getEmail());
        employeeSalvo.setFeedbackFeitos(employee.getFeedbackFeitos());
        employeeSalvo.setFeedbackRecebidos(employee.getFeedbackRecebidos());
        return dao.salvar(employeeSalvo);
    }

    @Override
    public void apagar(long id) throws BusinessException {
        dao.apagar(id);
    }

    @Override
    public void setDAO(DAO<Employee> dao) {
        this.dao = dao;
    }
}
