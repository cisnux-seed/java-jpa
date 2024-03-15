package dev.cisnux.javajpa.utils;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JpaUtil {
    private static EntityManagerFactory entityManagerFactory = null;

    public static EntityManagerFactory getEntityManagerFactory() {
        if (entityManagerFactory == null) {
            entityManagerFactory = Persistence.createEntityManagerFactory("CISNUX");
        }
        return entityManagerFactory;
    }

}
