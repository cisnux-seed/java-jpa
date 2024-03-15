package dev.cisnux.javajpa.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;
import java.util.Set;

@Entity
@Setter
@Getter
@Table(name = "products")
@With
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    @Id
    private String id;
    @ManyToOne(
            fetch = FetchType.LAZY
    )
    @JoinColumn(
            name = "brand_id",
            referencedColumnName = "id"
    )
    private Brand brand;
    private String name;
    private Long price;
    private String description;
    @ManyToMany(mappedBy = "likes")
    private Set<User> likedBy;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        Product product = (Product) o;
        return getId() != null && Objects.equals(getId(), product.getId());
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
                "price = " + price + ", " +
                "description = " + description + ")";
    }
}
