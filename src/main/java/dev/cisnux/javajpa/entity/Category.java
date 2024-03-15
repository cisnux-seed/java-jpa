package dev.cisnux.javajpa.entity;

import dev.cisnux.javajpa.listener.UpdatedAtListener;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Objects;

@Entity
@Table(name = "categories")
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
@EntityListeners({UpdatedAtListener.class})
public class Category implements UpdatedAtAware {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String description;
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "created_at")
    private Calendar createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Category category = (Category) o;
        return getId() != null && Objects.equals(getId(), category.getId());
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
                "description = " + description + ", " +
                "createdAt = " + createdAt + ", " +
                "updatedAt = " + updatedAt + ")";
    }
}
