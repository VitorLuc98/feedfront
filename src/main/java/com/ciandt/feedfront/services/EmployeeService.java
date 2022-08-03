package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.daos.EmployeeDAO;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EntidadeNaoEncontradaException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.excecoes.BusinessException;
import org.hibernate.exception.ConstraintViolationException;

import javax.persistence.PersistenceException;
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
        validaEmployee(employee);
        return dao.salvar(employee);
    }

    @Override
    public Employee atualizar(Employee employee) throws BusinessException {
        validaEmployee(employee);
        if (employee.getId()== null){
            throw new IllegalArgumentException("employee inválido: não possui ID");
        }
        return dao.salvar(employee);
    }

    @Override
    public void apagar(long id) throws BusinessException {
        buscar(id);
        dao.apagar(id);
    }

    private void validaEmployee(Employee employee) throws EmailInvalidoException {
        if (employee == null){
            throw new IllegalArgumentException();
        }
        boolean isEmailvalido = dao.listar().stream().anyMatch(x -> !x.getId().equals(employee.getId()) && x.getEmail().equals(employee.getEmail()));
        if (isEmailvalido){
            throw new EmailInvalidoException("já existe um employee cadastrado com esse e-mail");
        }
    }

    @Override
    public void setDAO(DAO<Employee> dao) {
        this.dao = dao;
    }
}
