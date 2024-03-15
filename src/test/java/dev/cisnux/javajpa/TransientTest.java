package dev.cisnux.javajpa;


import dev.cisnux.javajpa.entity.Customer;
import dev.cisnux.javajpa.entity.CustomerType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TransientTest extends CrudBaseTest {
    @Test
    void transientTest() {
        final var entityTransaction = entityManager.getTransaction();
        final var customer = new Customer();
        customer.setId("8");
        customer.setName("f2jra");
        customer.setAge((byte) 21);
        customer.setPrimaryEmail("fajra@gmail.com");
        customer.setIsMarried(false);
        customer.setType(CustomerType.PREMIUM);
        customer.setFullName("fajra risqulla");

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
