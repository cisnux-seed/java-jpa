package dev.cisnux.javajpa;

import dev.cisnux.javajpa.entity.Member;
import dev.cisnux.javajpa.entity.Name;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CollectionTest extends CrudBaseTest {
    @Test
    void create() {
        final var entityTransaction = entityManager.getTransaction();
        final var name = new Name();
        name.setTitle("Mr");
        name.setFirstName("fajra");
        name.setLastName("risqulla");
        name.setMiddleName("cisnux");

        final var member = new Member();
        member.setName(name);
        member.setEmail("fajra@gmail.com");
        member.setHobbies(List.of("gaming", "reading", "coding"));

        try {
            entityTransaction.begin();
            entityManager.persist(member);
            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void update() {
        final var entityTransaction = entityManager.getTransaction();
        final var member = entityManager.find(Member.class, 5);
        member.getHobbies().add("traveling");

        try {
            entityTransaction.begin();
            entityManager.merge(member);
            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void updateSkills() {
        final var entityTransaction = entityManager.getTransaction();
        final var member = entityManager.find(Member.class, 5);
        member.setSkills(new HashMap<>());
        member.getSkills().putAll(Map.of("Java", 90, "Kotlin", 100, "Dart", 100));

        try {
            entityTransaction.begin();
            entityManager.merge(member);
            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }
}
