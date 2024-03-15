package dev.cisnux.javajpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Embeddable
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Name {
    private String title;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "middle_name")
    private String middleName;
    @Column(name = "last_name")
    private String lastName;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Name name = (Name) o;
        return getTitle() != null && Objects.equals(getTitle(), name.getTitle())
                && getFirstName() != null && Objects.equals(getFirstName(), name.getFirstName())
                && getMiddleName() != null && Objects.equals(getMiddleName(), name.getMiddleName())
                && getLastName() != null && Objects.equals(getLastName(), name.getLastName());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(title,
                firstName,
                middleName,
                lastName);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "title = " + title + ", " +
                "firstName = " + firstName + ", " +
                "middleName = " + middleName + ", " +
                "lastName = " + lastName + ")";
    }
}
