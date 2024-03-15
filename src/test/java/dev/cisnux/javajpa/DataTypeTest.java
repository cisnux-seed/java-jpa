package dev.cisnux.javajpa;

import dev.cisnux.javajpa.entity.Customer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class DataTypeTest extends CrudBaseTest {
    @Test
    void dataType() {
        final var entityTransaction = entityManager.getTransaction();
        final var customer = new Customer();
        customer.setId("10");
        customer.setName("Fajra");
        customer.setAge((byte) 21);
        customer.setIsMarried(false);

        try {
            entityTransaction.begin();
            entityManager.persist(customer);
            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }
}
