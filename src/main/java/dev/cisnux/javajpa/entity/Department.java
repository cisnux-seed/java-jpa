package dev.cisnux.javajpa.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity
@Table(name = "departments")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Department {
    @EmbeddedId
    private DepartmentId id;
    private String name;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Department that = (Department) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "EmbeddedId = " + id + ", " +
                "name = " + name + ")";
    }
}
