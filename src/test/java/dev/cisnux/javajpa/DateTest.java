package dev.cisnux.javajpa;

import dev.cisnux.javajpa.entity.Category;
import dev.cisnux.javajpa.entity.Customer;
import dev.cisnux.javajpa.entity.CustomerType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Calendar;

public class DateTest extends CrudBaseTest {

    @Test
    void dateAndTime() {
        final var entityTransaction = entityManager.getTransaction();
        final var category = new Category();
        category.setName("Laptop");
        category.setDescription("Macbook");
        category.setCreatedAt(Calendar.getInstance());
        category.setUpdatedAt(LocalDateTime.now());

        try {
            entityTransaction.begin();
            entityManager.persist(category);
            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }
}
