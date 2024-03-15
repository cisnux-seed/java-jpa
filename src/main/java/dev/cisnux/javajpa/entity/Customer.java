package dev.cisnux.javajpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Entity(name = "customers")
@Setter
@Getter
@With
@RequiredArgsConstructor
@AllArgsConstructor
public class Customer {
    @Id
    @Column(name = "id")
    private String id;
    @Column(name = "name")
    private String name;
    @Column(name = "primary_email")
    private String primaryEmail;
    private Byte age;
    @Column(name = "married")
    private Boolean isMarried;
    @Enumerated(EnumType.STRING)
    private CustomerType type;
    @Transient
    private String fullName;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Customer customer = (Customer) o;
        return getId() != null && Objects.equals(getId(), customer.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "name = " + name + ", " +
                "primaryEmail = " + primaryEmail + ", " +
                "age = " + age + ", " +
                "isMarried = " + isMarried + ", " +
                "type = " + type + ", " +
                "fullName = " + fullName + ")";
    }
}
