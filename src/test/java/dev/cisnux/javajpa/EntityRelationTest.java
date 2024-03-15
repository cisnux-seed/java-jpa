package dev.cisnux.javajpa;

import dev.cisnux.javajpa.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.List;
import java.util.UUID;


public class EntityRelationTest extends CrudBaseTest {

    @Test
    void oneToOnePersist() {
        final var uuid = UUID.randomUUID().toString();
        final var entityTransaction = entityManager.getTransaction();
        final var credential = new Credential();
        credential.setId(uuid);
        credential.setEmail("fajra@gmail.com");
        credential.setPassword("cisnux123");

        final var user = new User();
        user.setId(uuid);
        user.setName("Fajra Risqulla");

        try {
            entityTransaction.begin();
            entityManager.persist(credential);
            entityManager.persist(user);
            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void oneToOneFind() {
        final var expectedUser = new User();
        expectedUser.setId("cfb44aab-0919-4ed8-a4e7-23edfefcaaa2");
        expectedUser.setName("Fajra Risqulla");

        final var credential = entityManager.find(Credential.class, "cfb44aab-0919-4ed8-a4e7-23edfefcaaa2");

        Assertions.assertNotNull(credential);
        Assertions.assertEquals(expectedUser.getName(), credential.getUser().getName());
    }

    @Test
    void oneToOneJoinColumn() {
        final var entityTransaction = entityManager.getTransaction();
        final var user = entityManager.find(User.class, "cfb44aab-0919-4ed8-a4e7-23edfefcaaa2");
        final var wallet = new Wallet();
        wallet.setUser(user);
        wallet.setBalance(1_000_000L);

        try {
            entityTransaction.begin();
            entityManager.persist(wallet);
            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void oneToOneJoinColumnFind() {
        final var wallet = new Wallet();
        wallet.setBalance(1_000_000L);

        final var user = entityManager.find(User.class, "cfb44aab-0919-4ed8-a4e7-23edfefcaaa2");

        Assertions.assertNotNull(user);
        Assertions.assertEquals(wallet.getBalance(), user.getWallet().getBalance());
    }

    @Test
    void oneToManyPersist() {
        final var entityTransaction = entityManager.getTransaction();
        final var brand = new Brand();
        brand.setId(UUID.randomUUID().toString());
        brand.setName("Samsung");
        brand.setDescription("sumsung's product");

        final var product1 = new Product();
        product1.setId(UUID.randomUUID().toString());
        product1.setName("Samsung Galaxy 1");
        product1.setBrand(brand);
        product1.setPrice(1_000_000L);

        final var product2 = new Product();
        product2.setId(UUID.randomUUID().toString());
        product2.setName("Samsung Galaxy 2");
        product2.setBrand(brand);
        product2.setPrice(2_000_000L);

        try {
            entityTransaction.begin();
            entityManager.persist(brand);
            entityManager.persist(product1);
            entityManager.persist(product2);
            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void oneToManyFind() {
        final var brand = entityManager.find(Brand.class, "b384d4f8-f7f9-4e2c-a15d-7ba35d6ad0c1");

        Assertions.assertFalse(brand.getProducts().isEmpty());
        Assertions.assertEquals(2, brand.getProducts().size());
    }

    @Test
    void manyToManyPersist() {
        final var entityTransaction = entityManager.getTransaction();
        final var user = entityManager.find(User.class, "cfb44aab-0919-4ed8-a4e7-23edfefcaaa2");
        final var product1 = entityManager.find(Product.class, "298939fd-8b5e-4f9e-bc8f-c6b664f8d694");
        final var product2 = entityManager.find(Product.class, "b90bb545-4502-476a-bce2-5338d479be78");

        user.setLikes(new HashSet<>());
        user.getLikes().addAll(List.of(product1, product2));

        try {
            entityTransaction.begin();
            entityManager.merge(user);
            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void manyToManyUpdate() {
        final var entityTransaction = entityManager.getTransaction();
        final var user = entityManager.find(User.class, "cfb44aab-0919-4ed8-a4e7-23edfefcaaa2");

        user.getLikes()
                .stream().findFirst().ifPresent(product -> {
                    try {
                        user.getLikes().remove(product);
                        entityTransaction.begin();
                        entityManager.merge(user);
                        entityTransaction.commit();
                    } catch (Throwable e) {
                        entityTransaction.rollback();
                        Assertions.fail(e);
                    }
                });
    }

    @Test
    void fetch() {
        final var product = entityManager.find(Product.class, "298939fd-8b5e-4f9e-bc8f-c6b664f8d694");
        // eager to lazy
        final var brand = product.getBrand();
        System.out.println(brand.getId());
        System.out.println(brand.getName());
        Assertions.assertNotNull(brand);
        // if id is equal to previous id then jpa will not query
        final var otherBrand = entityManager.find(Brand.class, "b384d4f8-f7f9-4e2c-a15d-7ba35d6ad0c1");
//        final var otherBrand = entityManager.find(Brand.class, "996b9e63-a4f8-455b-84eb-a0b1308ffe79");
        System.out.println(otherBrand.getId());
        System.out.println(otherBrand.getName());
        Assertions.assertNotNull(otherBrand);
    }
}
