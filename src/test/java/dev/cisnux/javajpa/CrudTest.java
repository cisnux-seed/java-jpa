package dev.cisnux.javajpa;

import dev.cisnux.javajpa.entity.Customer;
import org.junit.jupiter.api.*;

@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
public class CrudTest extends CrudBaseTest {

    @Order(1)
    @Test
    void insert() {
        final var entityTransaction = entityManager.getTransaction();
        final var customer = new Customer();
        customer.setId("1");
        customer.setName("Fajra");

        try {
            entityTransaction.begin();
            entityManager.persist(customer);
            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Order(2)
    @Test
    void find() {
        final var entityTransaction = entityManager.getTransaction();
        final var expectedCustomer = new Customer();
        expectedCustomer.setId("1");
        expectedCustomer.setName("Fajra");

        try {
            entityTransaction.begin();
            final var actualCustomer = entityManager.find(Customer.class, expectedCustomer.getId());
            entityTransaction.commit();
            Assertions.assertEquals(expectedCustomer, actualCustomer);
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Order(3)
    @Test
    void update() {
        final var entityTransaction = entityManager.getTransaction();
        final var expectedNewCustomer = new Customer();
        expectedNewCustomer.setId("1");
        expectedNewCustomer.setName("Cisnux");

        try {
            entityTransaction.begin();

            final var oldCustomer = entityManager.find(Customer.class, expectedNewCustomer.getId());
            final var newCustomer = oldCustomer.withName("Cisnux");

            entityManager.merge(newCustomer);
            final var actualNewCustomer = entityManager.find(Customer.class, newCustomer.getId());

            entityTransaction.commit();
            Assertions.assertEquals(expectedNewCustomer, actualNewCustomer);
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Order(4)
    @Test
    void delete() {
        final var entityTransaction = entityManager.getTransaction();
        final var id = "1";

        try {
            entityTransaction.begin();

            var customer = entityManager.find(Customer.class, id);
            entityManager.remove(customer);

            customer = entityManager.find(Customer.class, id);

            entityTransaction.commit();

            Assertions.assertNull(customer);
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }
}
