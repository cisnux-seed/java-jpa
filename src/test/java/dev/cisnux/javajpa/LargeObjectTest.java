package dev.cisnux.javajpa;

import dev.cisnux.javajpa.entity.Image;
import lombok.Cleanup;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;

public class LargeObjectTest extends CrudBaseTest {
    @Test
    @SneakyThrows
    void largeObject() {
        final var entityTransaction = entityManager.getTransaction();

        try {
            @Cleanup final var inputStream = LargeObjectTest.class.getResourceAsStream("/images/sample.png");
            Assertions.assertNotNull(inputStream);
            entityTransaction.begin();
            final var image = new Image();
            image.setName("profile");
            image.setDescription("foto profile");
            image.setImage(inputStream.readAllBytes());
            entityManager.persist(image);
            entityTransaction.commit();
        } catch (IOException e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    @SneakyThrows
    void otherLargeObject() {
        final var entityTransaction = entityManager.getTransaction();

        try {
            @Cleanup final var inputStream = LargeObjectTest.class.getResourceAsStream("/sample2.png");
            Assertions.assertNotNull(inputStream);
            entityTransaction.begin();
            final var image = new Image();
            image.setName("profile");
            image.setDescription("foto profile");
            image.setImage(inputStream.readAllBytes());
            entityManager.persist(image);
            entityTransaction.commit();
        } catch (IOException e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }
}
