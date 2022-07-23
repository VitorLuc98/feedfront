package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.excecoes.EntidadeNaoSerializavelException;
import com.ciandt.feedfront.models.Employee;

import java.io.*;
import java.util.List;

public class EmployeeDAO implements DAO<Employee> {
    private String repositorioPath = "";

    @Override
    public boolean tipoImplementaSerializable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Employee> listar() throws IOException, EntidadeNaoSerializavelException {
        throw new UnsupportedOperationException();
    }

    @Override
    public Employee buscar(String id) throws IOException, EntidadeNaoSerializavelException{
        throw new UnsupportedOperationException();
    }

    @Override
    public Employee salvar(Employee employee) throws IOException, EntidadeNaoSerializavelException {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean apagar(String id) throws IOException, EntidadeNaoSerializavelException {
        throw new UnsupportedOperationException();
    }
}
