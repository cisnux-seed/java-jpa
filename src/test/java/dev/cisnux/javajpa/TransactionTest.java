package dev.cisnux.javajpa;

import dev.cisnux.javajpa.utils.JpaUtil;
import lombok.Cleanup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransactionTest {
    @Test
    void transaction() {
        @Cleanup final var entityManagerFactory = JpaUtil.getEntityManagerFactory();
        @Cleanup final var entityManager = entityManagerFactory.createEntityManager();
        final var entityTransaction = entityManager.getTransaction();

        Assertions.assertNotNull(entityTransaction);

        try {
            entityTransaction.begin();
            // do something
            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
        }
    }
}
