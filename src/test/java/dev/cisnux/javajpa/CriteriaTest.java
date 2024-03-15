package dev.cisnux.javajpa;


import dev.cisnux.javajpa.entity.Brand;
import dev.cisnux.javajpa.entity.IdAndName;
import dev.cisnux.javajpa.entity.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class CriteriaTest extends CrudBaseTest {
    @Test
    void criteriaQuery() {
        try {
            entityTransaction.begin();

            final var criteria = criteriaBuilder.createQuery(Brand.class);
            final var b = criteria.from(Brand.class);
            criteria.select(b); // select b from Brand b

            final var query = entityManager.createQuery(criteria);
            query.getResultList().forEach(System.out::println);

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void criteriaQueryNonEntity() {
        try {
            entityTransaction.begin();

            final var criteria = criteriaBuilder.createQuery(Object[].class);
            final var b = criteria.from(Brand.class);
            criteria.select(criteriaBuilder.array(b.get("id"), b.get("name"))); // select b.id, b.name from Brand b

            final var query = entityManager.createQuery(criteria);
            final var idAndName = query.getResultList();

            idAndName.forEach(object -> System.out.println(object[0] + ":" + object[1]));

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void criteriaQueryConstructor() {
        try {
            entityTransaction.begin();

            final var criteria = criteriaBuilder.createQuery(IdAndName.class);
            final var b = criteria.from(Brand.class);
            criteria.select(criteriaBuilder.construct(IdAndName.class, b.get("id"), b.get("name"))); // select b.id, b.name from Brand b

            final var query = entityManager.createQuery(criteria);
            final var idAndName = query.getResultList();

            idAndName.forEach(item -> System.out.println(item.id() + ":" + item.name()));

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void criteriaQueryWhereClause() {
        try {
            entityTransaction.begin();

            final var criteria = criteriaBuilder.createQuery(Brand.class);
            final var b = criteria.from(Brand.class);
            criteria.select(b); // select b.id, b.name from Brand b where b.name = :name or b.createdAt is not null
            criteria.where(
                    criteriaBuilder.equal(b.get("name"), "Nokia Demo 24"),
                    criteriaBuilder.isNotNull(b.get("createdAt"))
            );

            final var query = entityManager.createQuery(criteria);
            query.getResultList().forEach(System.out::println);

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void criteriaQueryWhereClauseUsingOrOperator() {
        try {
            entityTransaction.begin();

            final var criteria = criteriaBuilder.createQuery(Brand.class);
            final var b = criteria.from(Brand.class);
            criteria.select(b); // select b.id, b.name from Brand b where b.name = :name or b.createdAt is not null
            criteria.where(
                    criteriaBuilder.or(
                            criteriaBuilder.equal(b.get("name"), "Oppo"),
                            criteriaBuilder.equal(b.get("name"), "Samsung")
                    )
            );

            final var query = entityManager.createQuery(criteria);
            query.getResultList().forEach(System.out::println);

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void criteriaJoinClause() {
        try {
            entityTransaction.begin();

            final var criteria = criteriaBuilder.createQuery(Product.class);
            final var p = criteria.from(Product.class);
            final var b = p.join("brand");


            criteria.select(p);  // select p from Product p join p.brand b
            criteria.where(
                    criteriaBuilder.equal(b.get("name"), "Samsung")
            ); // select p from Product p join p.brand b where b.name = 'Samsung'

            final var query = entityManager.createQuery(criteria);
            query.getResultList().forEach(System.out::println);

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void criteriaJoinClauseWithNamedParameter() {
        try {
            entityTransaction.begin();

            final var criteria = criteriaBuilder.createQuery(Product.class);
            final var p = criteria.from(Product.class);
            final var b = p.join("brand");

            criteria.select(p);  // select p from Product p join p.brand b
            final var brandParameter = criteriaBuilder.parameter(String.class, "brand");
            criteria.where(
                    criteriaBuilder.equal(b.get("name"), brandParameter)
            ); // select p from Product p join p.brand b where b.name = 'Samsung'


            final var query = entityManager.createQuery(criteria);
            query.setParameter(brandParameter, "Samsung");

            query.getResultList().forEach(System.out::println);

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void criteriaAggregateQuery() {
        try {
            entityTransaction.begin();

            final var criteria = criteriaBuilder.createQuery(Object[].class);
            final var p = criteria.from(Product.class);

            final var b = p.join("brand");

            criteria.select(criteriaBuilder.array(
                    b.get("name"),
                    criteriaBuilder.min(p.get("price")),
                    criteriaBuilder.max(p.get("price")),
                    criteriaBuilder.avg(p.get("price"))
            ));
            // select b.id, min(p.price), max(p.price), avg(p.price) from Product p join p.brand b

            criteria.groupBy(b.get("id"));
            // select b.id, min(p.price), max(p.price), avg(p.price) from Product p join p.brand b group by b.id

            criteria.having(criteriaBuilder.greaterThan(criteriaBuilder.min(p.get("price")), 500_000L));
            // select b.id, min(p.price), max(p.price), avg(p.price) from Product p join p.brand b group by b.id having min(p.price) > 500000

            final var query = entityManager.createQuery(criteria);

            query.getResultList().forEach(object -> {
                System.out.println("Brand : " + object[0]);
                System.out.println("Min : " + object[1]);
                System.out.println("Max : " + object[2]);
                System.out.println("Average : " + object[3]);
                System.out.println();
            });

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }

    @Test
    void criteriaNonQuery() {
        try {
            entityTransaction.begin();

            final var criteria = criteriaBuilder.createCriteriaUpdate(Brand.class);
            final var b = criteria.from(Brand.class);

            criteria.set(b.get("name"), "Apple Updated");
            criteria.set(b.get("description"), "Apple Company");

            criteria.where(
                    criteriaBuilder.equal(b.get("id"), "f5417719-13bf-4357-841c-622dcc72594c")
            );

            final var query = entityManager.createQuery(criteria);
            final var numberOfImpact = query.executeUpdate();
            System.out.println("Success update " + numberOfImpact + " records");

            entityTransaction.commit();
        } catch (Throwable e) {
            entityTransaction.rollback();
            Assertions.fail(e);
        }
    }
}
