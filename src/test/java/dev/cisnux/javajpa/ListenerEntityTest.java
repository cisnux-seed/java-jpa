package dev.cisnux.javajpa;

import dev.cisnux.javajpa.entity.Category;
import dev.cisnux.javajpa.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class ListenerEntityTest extends CrudBaseTest {
    @Test
    void listener() {
        final var entityTransaction = entityManager.getTransaction();
        final var category = new Category();
        category.setName("Ipad");
        category.setDescription("Samsung 9");

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

    @Test
    void entityListener() {
        final var member = entityManager.find(Member.class, 5);

        Assertions.assertEquals("Mr. fajra cisnux risqulla", member.getFullName());
    }
}
