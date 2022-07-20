package com.ciandt.feedfront.employee;

import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Employee implements Serializable {
    private static final long serialVersionUID = 1l;
    private String id;
    private String nome;
    private String sobrenome;
    private String email;

    private static final String REPOSITORIO_PATH = "src/main/resources/";
    private static List<Employee> employees = new ArrayList<>();

    private String arquivoCriado = "arquivo.extensao"; //TODO: alterar de acordo com a sua implementação

    public Employee(String nome, String sobrenome, String email) throws ComprimentoInvalidoException {
        setNome(nome);
        setSobrenome(sobrenome);
        this.id = UUID.randomUUID().toString();
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        arquivoCriado = REPOSITORIO_PATH + this.id + ".byte";
    }

    public static Employee salvarEmployee(Employee employee) throws ArquivoException, EmailInvalidoException {
        if (listarEmployees().stream().anyMatch(x -> !x.getId().equals(employee.getId()) && x.getEmail().equals(employee.getEmail()))) {
            throw new EmailInvalidoException("E-mail ja cadastrado no repositorio");
        }
        employees.add(employee);
        serializeEmployee(employee);
        return employee;
    }

    public static Employee atualizarEmployee(Employee employee) throws ArquivoException, EmailInvalidoException, EmployeeNaoEncontradoException, ComprimentoInvalidoException {
        buscarEmployee(employee.getId());
        return salvarEmployee(employee);
    }

    public static List<Employee> listarEmployees() throws ArquivoException {
        try {
            Stream<Path> paths = Files.walk(Paths.get(REPOSITORIO_PATH));
            List<String> files = paths
                    .map(p -> p.getFileName().toString())
                    .filter(p -> p.endsWith(".byte"))
                    .map(y -> y.replace(REPOSITORIO_PATH, ""))
                    .map(p -> p.replace(".byte", ""))
                    .collect(Collectors.toList());
            files.stream().map(y -> {
                try {
                    return employees.add(buscarEmployee(y));
                } catch (ArquivoException | EmployeeNaoEncontradoException e) {
                    throw new RuntimeException(e);
                }
            });
            paths.close();
        } catch (IOException e) {
            throw new ArquivoException("");
        }
        return employees;
    }

    public static Employee buscarEmployee(String id) throws ArquivoException, EmployeeNaoEncontradoException {
        return deserializeEmployee(id);
    }

    public static void apagarEmployee(String id) throws ArquivoException, EmployeeNaoEncontradoException {
        Employee employee = buscarEmployee(id);
        try {
            Files.delete(Paths.get(employee.arquivoCriado));
            employees.remove(employee);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) throws ComprimentoInvalidoException {
        verificaComprimento(nome, "nome");
        this.nome = nome;
    }

    public String getSobrenome() {
        return sobrenome;
    }

    public void setSobrenome(String sobrenome) throws ComprimentoInvalidoException {
        verificaComprimento(sobrenome, "sobrenome");
        this.sobrenome = sobrenome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return id;
    }

    public String getArquivoCriado() {
        return arquivoCriado;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id) && Objects.equals(email, employee.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, email);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", nome='" + nome + '\'' +
                ", sobrenome='" + sobrenome + '\'' +
                ", email='" + email + '\'' +
                ", arquivoCriado='" + arquivoCriado + '\'' +
                '}';
    }

    private void verificaComprimento(String atributo, String nomeAtributo) throws ComprimentoInvalidoException {
        if (atributo.length() <= 2) {
            throw new ComprimentoInvalidoException("Comprimento do " + nomeAtributo + " deve ser maior que 2 caracteres.");
        }
    }

    private static void serializeEmployee(Employee employee) throws ArquivoException {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(employee.arquivoCriado));
            objectOutputStream.writeObject(employee);
            objectOutputStream.close();
        } catch (IOException e) {
            throw new ArquivoException("");
        }
    }

    private static Employee deserializeEmployee(String idEmployee) throws ArquivoException, EmployeeNaoEncontradoException {
        ObjectInputStream objstream = null;
        Employee employee = null;
        try {
            objstream = new ObjectInputStream(new FileInputStream(REPOSITORIO_PATH + idEmployee + ".byte"));
            employee = (Employee) objstream.readObject();
            objstream.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new EmployeeNaoEncontradoException("Employee não encontrado");
        }
        return employee;
    }
}
