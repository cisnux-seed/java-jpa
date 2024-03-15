package dev.cisnux.javajpa;


import dev.cisnux.javajpa.entity.Brand;
import dev.cisnux.javajpa.entity.Member;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

public class ManagedEntityTest extends CrudBaseTest {

    @Test
    void mangedEntity() {
        // unmanaged entity
        final var brand = new Brand();
        brand.setId(UUID.randomUUID().toString());
        brand.setName("Apple");

        try {
            entityTransaction.begin();

            entityManager.persist(brand); // managed entity

            brand.setName("Apple International");
            // entityManager.merge(brand);

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void findManagedEntity() {
        try {
            entityTransaction.begin();

            final var brand = entityManager.find(Brand.class, "f5417719-13bf-4357-841c-622dcc72594c");
            brand.setName("Apple Indonesia");

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void findAnotherManagedEntity() {
        try {
            entityTransaction.begin();

            final var member = entityManager.find(Member.class, 5);
            member.getHobbies().add("football");

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void detachManagedEntity() {
        try {
            entityTransaction.begin();

            final var brand = entityManager.find(Brand.class, "f5417719-13bf-4357-841c-622dcc72594c");
            entityManager.detach(brand);
            brand.setName("Apple Test");

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void findManagedEntityAfterTransaction() {
        try {
            entityTransaction.begin();

            final var brand = entityManager.find(Brand.class, "f5417719-13bf-4357-841c-622dcc72594c");
            entityTransaction.commit();

            // doesn't has effect
            brand.setName("Orange Apple");
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }
}
