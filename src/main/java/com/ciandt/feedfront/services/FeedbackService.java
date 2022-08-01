package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.daos.FeedbackDAO;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.excecoes.EntidadeNaoEncontradaException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.models.Feedback;

import java.util.List;

public class FeedbackService implements Service<Feedback> {
    private DAO<Feedback> dao;
    private Service<Employee> employeeService;

    public FeedbackService() {
        setDAO(new FeedbackDAO());
        setEmployeeService(new EmployeeService());
    }

    @Override
    public List<Feedback> listar() {
        return dao.listar();
    }

    @Override
    public Feedback buscar(long id) throws BusinessException {
        return dao.buscar(id).orElseThrow(() -> new EntidadeNaoEncontradaException("Feedback não existe"));
    }

    @Override
    public Feedback salvar(Feedback feedback) throws BusinessException, IllegalArgumentException {
        if (feedback == null) {
            throw new IllegalArgumentException();
        }
        return dao.salvar(feedback);
    }

    @Override
    public Feedback atualizar(Feedback feedback) throws BusinessException, IllegalArgumentException {
        throw new UnsupportedOperationException(); // não implementar o método
    }

    @Override
    public void apagar(long id) throws BusinessException {
        throw new UnsupportedOperationException(); // não implementar o método
    }

    @Override
    public void setDAO(DAO<Feedback> dao) {
        this.dao = dao;
    }

    public void setEmployeeService(Service<Employee> employeeService) {
        this.employeeService = employeeService;
    }
}
