package dev.cisnux.javajpa;

import dev.cisnux.javajpa.entity.*;
import jakarta.persistence.EmbeddedId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class EmbeddedTest extends CrudBaseTest {
    @Test
    void embedded() {
        final var entityTransaction = entityManager.getTransaction();
        final var name = new Name();
        name.setTitle("Mr");
        name.setFirstName("fajra");
        name.setLastName("risqulla");
        name.setMiddleName("cisnux");

        final var member = new Member();
        member.setName(name);
        member.setEmail("fajra@gmail.com");

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
    void embeddedId() {
        final var entityTransaction = entityManager.getTransaction();
        final var departmentId = new DepartmentId();
        departmentId.setCompanyId("company-1");
        departmentId.setDepartmentId("department-1");

        final var department = new Department();
        department.setName("Solution Development");
        department.setId(departmentId);

        try {
            entityTransaction.begin();
            entityManager.persist(department);
            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void findEmbeddedId() {
        final var expectedDepartmentId = new DepartmentId();
        expectedDepartmentId.setCompanyId("company-1");
        expectedDepartmentId.setDepartmentId("department-1");

        final var expectedDepartment = new Department();
        expectedDepartment.setName("Solution Development");
        expectedDepartment.setId(expectedDepartmentId);

        final var actualDepartment = entityManager.find(Department.class, expectedDepartmentId);
        Assertions.assertEquals(expectedDepartment, actualDepartment);
    }
}
