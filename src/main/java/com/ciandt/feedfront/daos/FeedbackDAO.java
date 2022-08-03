package com.ciandt.feedfront.daos;

import com.ciandt.feedfront.contracts.DAO;
import com.ciandt.feedfront.models.Feedback;
import com.ciandt.feedfront.utils.PersistenceUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class FeedbackDAO implements DAO<Feedback> {
    private EntityManager entityManager;

    public FeedbackDAO() {
        setEntityManager(PersistenceUtil.getEntityManager());
    }

    @Override
    public List<Feedback> listar() {
        String jpql = "SELECT f FROM Feedback f";
        return entityManager.createQuery(jpql, Feedback.class).getResultList();
    }

    @Override
    public Optional<Feedback> buscar(long id) {
        Feedback feedback = entityManager.find(Feedback.class, id);
        return Optional.ofNullable(feedback);

    }

    @Override
    public Feedback salvar(Feedback feedback) {
        abrirT();
        feedback = entityManager.merge(feedback);
        fecharT();
        return feedback;
    }

    @Override
    public boolean apagar(long id) {
        Optional<Feedback> feedback = buscar(id);
        if (feedback.isPresent()) {
            abrirT();
            entityManager.remove(feedback.get());
            fecharT();
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
