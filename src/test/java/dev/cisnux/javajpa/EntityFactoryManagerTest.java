package dev.cisnux.javajpa;

import dev.cisnux.javajpa.utils.JpaUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EntityFactoryManagerTest {
    @Test
    void create() {
        final var entityManagerFactory = JpaUtil.getEntityManagerFactory();
        Assertions.assertNotNull(entityManagerFactory);
    }
}
