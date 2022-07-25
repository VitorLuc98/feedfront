package com.ciandt.feedfront.services;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.contracts.Service;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.BusinessException;
import com.ciandt.feedfront.models.Feedback;

import java.io.IOException;
import java.util.List;

public class FeedbackService implements Service<Feedback> {

    private DAO<Feedback> dao;

    public FeedbackService(){
        setDAO(dao);
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
            throw new BusinessException("Feedback não encontrado");
        }
    }

    @Override
    public Feedback salvar(Feedback feedback) throws ArquivoException, BusinessException, IllegalArgumentException {
        try {
            return dao.salvar(feedback);
        } catch (IOException e) {
            throw new BusinessException("");
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
}
