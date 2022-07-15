package com.ciandt.feedfront.employee;

import com.ciandt.feedfront.excecoes.ArquivoException;
import com.ciandt.feedfront.excecoes.ComprimentoInvalidoException;
import com.ciandt.feedfront.excecoes.EmailInvalidoException;
import com.ciandt.feedfront.excecoes.EmployeeNaoEncontradoException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class Employee implements Serializable {
    private static final long serialVersionUID = 1l;
    private String id;
    private String nome;
    private String sobrenome;
    private String email;

    private static final String REPOSITORIO_PATH= "src/main/resources/";
    private static List<Employee> employees = new ArrayList<>();

    String arquivoCriado = "arquivo.extensao"; //TODO: alterar de acordo com a sua implementação

    public Employee(String nome, String sobrenome, String email) throws ComprimentoInvalidoException {
        this.id = UUID.randomUUID().toString();
        verificaComprimento(nome, "nome");
        verificaComprimento(sobrenome, "sobrenome");
        this.nome = nome;
        this.sobrenome = sobrenome;
        this.email = email;
        arquivoCriado = REPOSITORIO_PATH+this.id+".bytes";
    }

    public static Employee salvarEmployee(Employee employee) throws ArquivoException, EmailInvalidoException {
        if (employees.stream().anyMatch(x -> x.getEmail().equals(employee.getEmail()))){
            throw new EmailInvalidoException("E-mail já cadastrado no repositorio");
        }
        serializeEmployees(employee);
        employees.add(employee);
        deserializeEmployees(employee);
        return employee;
    }

    public static Employee atualizarEmployee(Employee employee) throws ArquivoException, EmailInvalidoException, EmployeeNaoEncontradoException, ComprimentoInvalidoException {
        Employee employee1 = buscarEmployee(employee.getId());
        employee1.setNome(employee.getNome());
        employee1.setSobrenome(employee.getSobrenome());
        employee1.setEmail(employee.getEmail());
        return employee1;
    }

    public static List<Employee> listarEmployees() throws ArquivoException {
        return employees;
    }

    public static Employee buscarEmployee(String id) throws ArquivoException, EmployeeNaoEncontradoException {
        return employees.stream().filter(x -> x.getId().equals(id)).
                findFirst().orElseThrow( () -> new EmployeeNaoEncontradoException("Employee não encontrado"));
    }

    public static void apagarEmployee(String id) throws ArquivoException, EmployeeNaoEncontradoException {
        Employee employee = buscarEmployee(id);
        employees.remove(employee);
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
        if (atributo.length() <= 2){
            throw new ComprimentoInvalidoException("Comprimento do "+nomeAtributo+" deve ser maior que 2 caracteres.");
        }
    }

    private static void serializeEmployees(Employee employee) throws ArquivoException {
        ObjectOutputStream objectOutputStream = null;
        try {
            objectOutputStream = new ObjectOutputStream(new FileOutputStream(employee.arquivoCriado));
            objectOutputStream.writeObject(employee);
            objectOutputStream.close();
        } catch (IOException e) {
            throw new ArquivoException("");
        }
    }

    private static void deserializeEmployees(Employee employee) throws ArquivoException {
        ObjectInputStream objstream = null;
        try {
            objstream = new ObjectInputStream(new FileInputStream(employee.arquivoCriado));
            Employee employee1 = (Employee) objstream.readObject();
            objstream.close();
        } catch (IOException | ClassNotFoundException e) {
            throw new ArquivoException("");
        }
    }
}
