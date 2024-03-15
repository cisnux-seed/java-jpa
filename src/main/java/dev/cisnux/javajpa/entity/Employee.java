package dev.cisnux.javajpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Table(name = "employees")
@DiscriminatorColumn(name = "type")
@DiscriminatorValue(value = "EMPLOYEE")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@With
public class Employee {
    @Id
    private String id;
    private String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Employee employee = (Employee) o;
        return getId() != null && Objects.equals(getId(), employee.getId());
    }

    @Override
    public int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ")";
    }
}
