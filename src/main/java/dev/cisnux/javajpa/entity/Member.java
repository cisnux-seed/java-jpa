package dev.cisnux.javajpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@Entity
@Table(name = "members")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String email;
    @Embedded
    private Name name;

    @ElementCollection
    @CollectionTable(name = "hobbies", joinColumns = @JoinColumn(
            name = "member_id", referencedColumnName = "id"
    ))
    // column name of embedded collection
    @Column(name = "name")
    private List<String> hobbies;

    @ElementCollection
    @CollectionTable(name = "skills", joinColumns = @JoinColumn(
            name = "member_id", referencedColumnName = "id"
    ))
    @MapKeyColumn(name = "name")
    @Column(name = "value")
    private Map<String, Integer> skills;
    @Transient
    private String fullName;

    @PostLoad
    public void postLoad() {
        fullName = name.getTitle() + ". " + name.getFirstName() + " " + name.getMiddleName() + " " + name.getLastName();
    }

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Member member = (Member) o;
        return getId() != null && Objects.equals(getId(), member.getId());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "id = " + id + ", " +
                "email = " + email + ", " +
                "name = " + name + ", " +
                "hobbies = " + hobbies + ", " +
                "skills = " + skills + ", " +
                "fullName = " + fullName + ")";
    }
}
