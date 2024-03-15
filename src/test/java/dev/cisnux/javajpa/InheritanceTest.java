package dev.cisnux.javajpa;

import dev.cisnux.javajpa.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

public class InheritanceTest extends CrudBaseTest {
    @Test
    void singleTableInsert() {
        final var employee = new Employee();
        employee.setId(UUID.randomUUID().toString());
        employee.setName("Fajra Risqulla");
        System.out.println(employee);

        final var manager = new Manager();
        manager.setId(UUID.randomUUID().toString());
        manager.setName("Cisnux");
        manager.setTotalEmployee(100);
        System.out.println(employee);

        final var vp = new VicePresident();
        vp.setId(UUID.randomUUID().toString());
        vp.setName("Fajra Cisnux");
        vp.setTotalManager(10);
        System.out.println(vp);

        try {
            entityTransaction.begin();

            entityManager.persist(employee);
            entityManager.persist(manager);
            entityManager.persist(vp);

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
        }
    }

    @Test
    void singleTableFind() {
        // select by 1 table
        final var manager = entityManager.find(Manager.class, "d88f612a-5d78-42a1-9d1a-a8b5a168d506");
        final var vp = ((VicePresident) entityManager.find(Employee.class, "85b554a3-99de-4e3d-85db-5921614cfa06"));

        System.out.println(manager);
        System.out.println(vp);

        Assertions.assertNotNull(manager);
        Assertions.assertNotNull(vp);
    }

    @Test
    void joinedTableInsert() {
        final var gopay = new GopayPayment();
        gopay.setId(UUID.randomUUID().toString());
        gopay.setAmount(100_000L);
        gopay.setGopayId("089999999999");
        System.out.println(gopay);

        final var creditCard = new CreditCardPayment();
        creditCard.setId(UUID.randomUUID().toString());
        creditCard.setAmount(500_000L);
        creditCard.setMaskedCard("4555-5555");
        creditCard.setBank("BCA");
        System.out.println(creditCard);

        try {
            entityTransaction.begin();

            entityManager.persist(gopay);
            entityManager.persist(creditCard);

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
        }
    }

    @Test
    void joinedTableFind() {
        // select by join
        final var gopay = entityManager.find(GopayPayment.class, "419742ed-06ba-4e4e-85b6-abdeb71e863f");
        final var creditCard = entityManager.find(CreditCardPayment.class, "47618beb-3f2e-48f9-998f-5acfd73a315f");

        System.out.println(gopay);
        System.out.println(creditCard);

        Assertions.assertNotNull(gopay);
        Assertions.assertNotNull(creditCard);
    }

    @Test
    void tablePerClassInsert() {
        final  var transaction = new Transaction();
        transaction.setId(UUID.randomUUID().toString());
        transaction.setCreatedAt(LocalDateTime.now());
        transaction.setBalance(1_000_000L);
        System.out.println(transaction);

        final var debitTransaction = new DebitTransaction();
        debitTransaction.setId(UUID.randomUUID().toString());
        debitTransaction.setCreatedAt(LocalDateTime.now());
        debitTransaction.setBalance(2_000_000L);
        debitTransaction.setDebitAmount(1_000_000L);
        System.out.println(debitTransaction);

        final var creditTransaction = new CreditTransaction();
        creditTransaction.setId(UUID.randomUUID().toString());
        creditTransaction.setCreatedAt(LocalDateTime.now());
        creditTransaction.setBalance(1_000_000L);
        creditTransaction.setCreditAmount(1_000_000L);
        System.out.println(creditTransaction);

        try {
            entityTransaction.begin();

            entityManager.persist(transaction);
            entityManager.persist(creditTransaction);
            entityManager.persist(debitTransaction);

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
        }
    }

    @Test
    void tablePerClassFind() {
        // select by subquery
        final var transaction = entityManager.find(Transaction.class, "5e12b829-82c7-4144-8bab-4844146ff1aa");
        final var debitTransaction = entityManager.find(DebitTransaction.class, "5bab79b3-4722-4a53-b57f-a233cce7b273");
        final var creditTransaction = entityManager.find(CreditTransaction.class, "45cc25f9-7147-481e-9cbe-1e3f1699271d");

        System.out.println(transaction);
        System.out.println(debitTransaction);
        System.out.println(creditTransaction);

        Assertions.assertNotNull(transaction);
        Assertions.assertNotNull(debitTransaction);
        Assertions.assertNotNull(creditTransaction);
    }
}
