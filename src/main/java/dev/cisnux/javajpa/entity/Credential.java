package dev.cisnux.javajpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@With
@Entity
@Table(name = "credentials")
public class Credential {
    @Id
    private String id;
    private String email;
    private String password;
    @OneToOne(mappedBy = "credential")
    private User user;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Credential that = (Credential) o;
        return getId() != null && Objects.equals(getId(), that.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return "Credential{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", user=" + user +
                '}';
    }
}
