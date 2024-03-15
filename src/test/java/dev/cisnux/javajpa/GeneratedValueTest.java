package dev.cisnux.javajpa;

import dev.cisnux.javajpa.entity.Category;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class GeneratedValueTest extends CrudBaseTest {

    @Test
    void generatedValueTest() {
        final var entityTransaction = entityManager.getTransaction();
        final var category = new Category();
        category.setName("Gadget");
        category.setDescription("Samsung");

        try {
            entityTransaction.begin();
            entityManager.persist(category);
            Assertions.assertNotNull(category.getId());
            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }
}
