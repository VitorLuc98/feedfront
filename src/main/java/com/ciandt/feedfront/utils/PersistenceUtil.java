package com.ciandt.feedfront.utils;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceUtil {
    private static final  EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("feedfront");

    public static EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }

}
