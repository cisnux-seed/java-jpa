package dev.cisnux.javajpa;

import dev.cisnux.javajpa.entity.Brand;
import dev.cisnux.javajpa.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

public class MapperSuperClassTest extends CrudBaseTest {
    @Test
    void mappedSupperClass() {
        final var brand = new Brand();
        brand.setId(UUID.randomUUID().toString());
        brand.setName("Oppo");
        brand.setDescription("oppo's product");
        brand.setCreatedAt(LocalDateTime.now());
        brand.setUpdatedAt(LocalDateTime.now());

        final var product1 = new Product();
        product1.setId(UUID.randomUUID().toString());
        product1.setName("Oppo Galaxy 1");
        product1.setBrand(brand);
        product1.setPrice(1_000_000L);

        final var product2 = new Product();
        product2.setId(UUID.randomUUID().toString());
        product2.setName("Oppo Galaxy 2");
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
}
