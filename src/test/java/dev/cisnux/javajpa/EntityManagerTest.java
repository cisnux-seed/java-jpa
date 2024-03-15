package dev.cisnux.javajpa;

import dev.cisnux.javajpa.utils.JpaUtil;
import lombok.Cleanup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EntityManagerTest {

    // with lombok
    @Test
    void createEntityManagerTestThenCloseAutomaticallyByLombok() {
        @Cleanup final var entityManagerFactory = JpaUtil.getEntityManagerFactory();
        @Cleanup final var entityManager = entityManagerFactory.createEntityManager();

        Assertions.assertNotNull(entityManagerFactory);
        Assertions.assertNotNull(entityManager);
    }

    @Test
    void createEntityManagerTestThenCloseAutomaticallyByTryResource() {
        try (final var entityManagerFactory = JpaUtil.getEntityManagerFactory()) {
            try (final var entityManager = entityManagerFactory.createEntityManager()) {
                // do something
                Assertions.assertNotNull(entityManagerFactory);
                Assertions.assertNotNull(entityManager);
            }
        }
    }

    @Test
    void createEntityManagerTestThenCloseManually() {
        final var entityManagerFactory = JpaUtil.getEntityManagerFactory();
        final var entityManager = entityManagerFactory.createEntityManager();

        entityManagerFactory.close();
        entityManager.close();
    }
}
