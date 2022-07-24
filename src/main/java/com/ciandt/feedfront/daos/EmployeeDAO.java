package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;
import com.ciandt.feedfront.excecoes.EntidadeNaoSerializavelException;
import com.ciandt.feedfront.models.Employee;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EmployeeDAO implements DAO<Employee> {

    private static final String REPOSITORIO_PATH = "src/main/resources/data/employee/";

    @Override
    public boolean tipoImplementaSerializable() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Employee> listar() throws IOException, EntidadeNaoSerializavelException {
        List<Employee> employees = new ArrayList<>();
        try {
            Stream<Path> repoPath = Files.walk(Paths.get(REPOSITORIO_PATH));
            List<String> files = buscarArquivosPorPath(repoPath);

            files.stream().forEach(file -> {
                try {
                    employees.add(buscar(file));
                } catch (EntidadeNaoSerializavelException | IOException e) {
                    throw new RuntimeException(e);
                }
            });
            repoPath.close();
        } catch (IOException e) {
            throw new ArquivoException("");
        }
        return employees;
    }

    @Override
    public Employee buscar(String id) throws IOException, EntidadeNaoSerializavelException{
        ObjectInputStream objstream = null;
        Employee employee = null;
        try {
            objstream = new ObjectInputStream(new FileInputStream(REPOSITORIO_PATH + id + ".byte"));
            employee = (Employee) objstream.readObject();
            objstream.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new EntidadeNaoSerializavelException();
        }
        return employee;
    }

    @Override
    public Employee salvar(Employee employee) throws IOException, EntidadeNaoSerializavelException {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(employee.getArquivoCriado()));
            objectOutputStream.writeObject(employee);
            objectOutputStream.close();
        } catch (IOException e) {
            throw new EntidadeNaoSerializavelException();
        }
        return employee;
    }

    @Override
    public boolean apagar(String id) throws IOException, EntidadeNaoSerializavelException {
        try{
            Employee employee = buscar(id);
            Files.delete(Paths.get(employee.getArquivoCriado()));
            listar().remove(employee);
        }catch (IOException e){
           throw new EntidadeNaoSerializavelException();
        }
        return true;
    }
    private List<String> buscarArquivosPorPath(Stream<Path> paths){
        return paths.map(p -> p.getFileName().toString())
                .filter(p -> p.endsWith(".byte"))
                .map(y -> y.replace(REPOSITORIO_PATH, ""))
                .map(p -> p.replace(".byte", ""))
                .collect(Collectors.toList());
    }
}
