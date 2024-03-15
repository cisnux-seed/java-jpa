package dev.cisnux.javajpa;

import dev.cisnux.javajpa.entity.Customer;
import dev.cisnux.javajpa.entity.CustomerType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EnumTest extends CrudBaseTest{
    @Test
    void enumTest() {
        final var entityTransaction = entityManager.getTransaction();
        final var customer = new Customer();
        customer.setId("2");
        customer.setName("Fajra");
        customer.setAge((byte) 21);
        customer.setPrimaryEmail("fajra@gmail.com");
        customer.setIsMarried(false);
        customer.setType(CustomerType.VIP);

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
