package dev.cisnux.javajpa;


import dev.cisnux.javajpa.entity.Brand;
import jakarta.persistence.LockModeType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

public class LockingTest extends CrudBaseTest {
    @Test
    void optimisticLockingInsert() {
        final var brand = new Brand();
        brand.setId(UUID.randomUUID().toString());
        brand.setName("Nokia");
        brand.setDescription("nokia's product");
        brand.setCreatedAt(LocalDateTime.now());
        brand.setUpdatedAt(LocalDateTime.now());

        try {
            entityTransaction.begin();

            entityManager.persist(brand);

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void optimisticLockingDemo1() {
        final var brand = entityManager.find(Brand.class, "8dcd19f0-6c27-4370-a930-9c89e8677b64");
        brand.setName("Nokia Demo 1");
        brand.setUpdatedAt(LocalDateTime.now());

        System.out.println(brand);
        try {
            entityTransaction.begin();
            Thread.sleep(10 * 1000L);
            entityManager.persist(brand);
            entityTransaction.commit();

        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void optimisticLockingDemo2() {
        final var brand = entityManager.find(Brand.class, "8dcd19f0-6c27-4370-a930-9c89e8677b64");
        brand.setName("Nokia Demo 22");
        brand.setUpdatedAt(LocalDateTime.now());

        System.out.println(brand);
        try {
            entityTransaction.begin();
            entityManager.persist(brand);
            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void pessimisticLockingDemo1() {
        try {
            // to use pessimistic locking the find method must under begin
            entityTransaction.begin();

            final var brand = entityManager.find(Brand.class, "8dcd19f0-6c27-4370-a930-9c89e8677b64", LockModeType.PESSIMISTIC_WRITE);
            brand.setName("Nokia Demo 14");
            brand.setUpdatedAt(LocalDateTime.now());
            System.out.println(brand);

            Thread.sleep(10 * 1000L);
            entityManager.persist(brand);
            entityTransaction.commit();

        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void pessimisticLockingDemo2() {
        try {
            // to use pessimistic locking the find method must under begin
            entityTransaction.begin();

            final var brand = entityManager.find(Brand.class, "8dcd19f0-6c27-4370-a930-9c89e8677b64", LockModeType.PESSIMISTIC_WRITE);
            brand.setName("Nokia Demo 24");
            brand.setUpdatedAt(LocalDateTime.now());
            System.out.println(brand);

            entityManager.persist(brand);
            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }
}
