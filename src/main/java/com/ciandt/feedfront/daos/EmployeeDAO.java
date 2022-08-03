package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.models.Employee;
import com.ciandt.feedfront.utils.PersistenceUtil;
import org.hibernate.Session;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class EmployeeDAO implements DAO<Employee> {
    private EntityManager entityManager;

    public EmployeeDAO() {
        this.entityManager = PersistenceUtil.getEntityManager();
    }

    @Override
    public List<Employee> listar() {
        String jpql = "SELECT e FROM Employee e";
        TypedQuery<Employee> query = entityManager.createQuery(jpql, Employee.class);
        return query.getResultList();
    }

    @Override
    public Optional<Employee> buscar(long id) {
        Employee employee = entityManager.find(Employee.class, id);
        return Optional.ofNullable(employee);
    }

    @Override
    public Employee salvar(Employee employee) {
        abrirT();
        employee = entityManager.merge(employee);
        fecharT();
        entityManager.clear();
        return employee;
    }

    @Override
    public boolean apagar(long id) {
        Optional<Employee> employee = buscar(id);
        if (employee.isPresent()) {
            abrirT();
            entityManager.remove(employee.get());
            fecharT();
            entityManager.clear();
            return true;
        }
        return false;
    }

    @Override
    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    private void abrirT() {
        entityManager.getTransaction().begin();
    }

    private void fecharT() {
        entityManager.getTransaction().commit();
    }
}
