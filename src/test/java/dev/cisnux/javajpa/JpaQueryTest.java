package dev.cisnux.javajpa;

import dev.cisnux.javajpa.entity.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

public class JpaQueryTest extends CrudBaseTest {
    @Test
    void select() {
        try {
            entityTransaction.begin();

            final var query = entityManager.createQuery("select b from Brand b", Brand.class);
            query.getResultList().forEach(System.out::println);

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void selectProduct() {
        try {
            entityTransaction.begin();

            final var query = entityManager.createQuery("select p from Product p", Product.class);
            final var products = query.getResultList();
            products.forEach(System.out::println);

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void whereClause() {
        try {
            entityTransaction.begin();

            final var query = entityManager.createQuery("select m from Member m " + "where m.name.firstName = :firstName and m.name.lastName = :lastName", Member.class);
            query.setParameter("firstName", "fajra");
            query.setParameter("lastName", "risqulla");
            final var member = query.getSingleResult();

            System.out.println(member);

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void joinClause() {
        try {
            entityTransaction.begin();

            final var query = entityManager.createQuery("select p from Product p join p.brand b where b.name = :brand", Product.class);
            query.setParameter("brand", "Samsung");
            final var products = query.getResultList();

            products.forEach(System.out::println);

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void joinFetchClause() {
        try {
            entityTransaction.begin();

            final var query = entityManager.createQuery("select u from User u join fetch u.likes p where p.name = :product", User.class);
            query.setParameter("product", "Samsung Galaxy 2");
            final var products = query.getResultList();

            products.stream().map(User::getName).forEach(System.out::println);

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void orderClause() {
        try {
            entityTransaction.begin();

            final var query = entityManager.createQuery("select b from Brand b order by b.name desc", Brand.class);
            final var brands = query.getResultList();

            brands.stream().map(Brand::getName).forEach(System.out::println);

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void insertRandomBrand() {
        try {
            entityTransaction.begin();

            for (int i = 0; i < 100; i++) {
                final var brand = new Brand();
                brand.setId(UUID.randomUUID().toString());
                brand.setName("Brand " + i);
                entityManager.persist(brand);
            }

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void limitOffset() {
        try {
            entityTransaction.begin();

            final var query = entityManager.createQuery("select b from Brand b order by b.name desc", Brand.class);
            query.setFirstResult(10);
            query.setMaxResults(10);
            final var brands = query.getResultList();

            brands.stream().map(Brand::getName).forEach(System.out::println);

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void namedQuery() {
        try {
            entityTransaction.begin();

            final var query = entityManager.createNamedQuery("Brand.findAllByName", Brand.class);
            query.setParameter("name", "Xiaomi");
            final var brands = query.getResultList();

            brands.stream().map(Brand::getName).forEach(System.out::println);

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void selectSomeFields() {
        try {
            entityTransaction.begin();

            final var query = entityManager.createQuery("select b.id, b.name from Brand b where b.name = :name", Object[].class);
            query.setParameter("name", "Xiaomi");
            final var idAndName = query.getResultList();

            idAndName.forEach(object -> System.out.println(object[0] + ":" + object[1]));

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void selectNewConstructor() {
        try {
            entityTransaction.begin();

            final var query = entityManager.createQuery("select new dev.cisnux.javajpa.entity.IdAndName(b.id, b.name) from Brand b where b.name = :name", IdAndName.class);
            query.setParameter("name", "Xiaomi");
            final var idAndName = query.getResultList();

            idAndName.forEach(item -> System.out.println(item.id() + ":" + item.name()));

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void aggregateQuery() {
        try {
            entityTransaction.begin();

            final var query = entityManager.createQuery("select min(p.price), max(p.price), avg(p.price) from Product p", Object[].class);
            final var result = query.getSingleResult();

            System.out.println("Min : " + result[0]);
            System.out.println("Max : " + result[1]);
            System.out.println("Average : " + result[2]);

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void aggregateQueryGroupBy() {
        try {
            entityTransaction.begin();

            final var query = entityManager.createQuery("select b.name, min(p.price), max(p.price), avg(p.price) from Product p join p.brand b group by b.name having min(p.price) >= :min", Object[].class);
            query.setParameter("min", 500_000L);
            final var results = query.getResultList();

            results.forEach(result -> {
                System.out.println("Brand Name : " + result[0]);
                System.out.println("Min : " + result[1]);
                System.out.println("Max : " + result[2]);
                System.out.println("Average : " + result[3]);
                System.out.println();
            });

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void nativeQuery() {
        try {
            entityTransaction.begin();

            final var query = entityManager.createNativeQuery("select * from brands where brands.created_at is not null", Brand.class);
            final List<Brand> brands = query.getResultList();

            brands.stream().map(Brand::getName).forEach(System.out::println);

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void namedNativeQuery() {
        try {
            entityTransaction.begin();

            final var query = entityManager.createNamedQuery("Brand.native.findAll", Brand.class);
            final var brands = query.getResultList();

            brands.stream().map(Brand::getName).forEach(System.out::println);

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void nonQuery() {
        try {
            entityTransaction.begin();

            final var query = entityManager.createQuery("update Brand b set b.name = :name where b.id = :id");
            query.setParameter("id", "972fc90c-61c4-4511-ac8b-14934ca1488d");
            query.setParameter("name", "Oppo Updated");

            final var numberOfImpact = query.executeUpdate();
            System.out.println("Success update " + numberOfImpact + " records");

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }
}
