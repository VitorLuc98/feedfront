package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.daos.EmployeeDAO;
import com.ciandt.feedfront.daos.FeedbackDAO;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.excecoes.EntidadeNaoEncontradaException;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.models.Feedback;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class FeedbackService implements Service<Feedback> {

    private DAO<Feedback> dao;

    private Service<Employee> employeeService;

    public FeedbackService() {
        setDAO(new FeedbackDAO());
        setEmployeeService(new EmployeeService());
    }

    @Override
    public List<Feedback> listar() throws ArquivoException {
        try {
            return dao.listar();
        } catch (IOException e) {
            throw new ArquivoException("");
        }
    }

    @Override
    public Feedback buscar(String id) throws ArquivoException, BusinessException {
        try {
            return dao.buscar(id);
        } catch (IOException e) {
            throw new EntidadeNaoEncontradaException("não foi possível encontrar o feedback");
        }
    }

    @Override
    public Feedback salvar(Feedback feedback) throws ArquivoException, BusinessException {
        verificaFeedback(feedback);
        try {
            return dao.salvar(feedback);
        } catch (IOException e) {
            throw new EntidadeNaoEncontradaException("");
        }
    }

    @Override
    public Feedback atualizar(Feedback feedback) throws ArquivoException, BusinessException, IllegalArgumentException {
        Feedback employeeSalvo = buscar(feedback.getId());
        try {
            return dao.salvar(employeeSalvo);
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
    public void setDAO(DAO<Feedback> dao) {
        this.dao = dao;
    }

    private void verificaFeedback(Feedback feedback) throws BusinessException, ArquivoException {
        if (feedback == null) {
            throw new IllegalArgumentException("feedback inválido");
        }
        if (feedback.getProprietario() == null) {
            throw new IllegalArgumentException("employee inválido");
        }
//    Employee employee = employeeService.buscar(feedback.getProprietario().getId());
    }

    public void setEmployeeService(Service<Employee> employeeService) {
        this.employeeService = employeeService;
    }

}
