package dev.cisnux.javajpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;


@Entity
@DiscriminatorValue("MANAGER")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@With
public class Manager extends Employee {
    @Column(name = "total_employee")
    private Integer totalEmployee;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Manager manager = (Manager) o;
        return getId() != null && Objects.equals(getId(), manager.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + getId() + ", " +
                "name = " + getName() + ", " +
                "totalEmployee = " + getTotalEmployee() + ")";
    }
}
