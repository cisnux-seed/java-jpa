package dev.cisnux.javajpa;

import dev.cisnux.javajpa.utils.JpaUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInstance;

@TestInstance(value = TestInstance.Lifecycle.PER_CLASS)
public class CrudBaseTest {
    protected EntityManagerFactory entityManagerFactory;
    protected EntityManager entityManager;
    protected EntityTransaction entityTransaction;
    protected CriteriaBuilder criteriaBuilder;

    @BeforeAll
    void beforeAll() {
        entityManagerFactory = JpaUtil.getEntityManagerFactory();
        entityManager = entityManagerFactory.createEntityManager();
        entityTransaction = entityManager.getTransaction();
        criteriaBuilder = entityManager.getCriteriaBuilder();
    }

    @AfterAll
    void afterAll() {
        entityManagerFactory.close();
        entityManager.close();
    }
}
