package dev.cisnux.javajpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentId implements Serializable {
    @Column(name = "company_id")
    private String companyId;
    @Column(name = "department_id")
    private String departmentId;

    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        DepartmentId that = (DepartmentId) o;
        return getCompanyId() != null && Objects.equals(getCompanyId(), that.getCompanyId())
                && getDepartmentId() != null && Objects.equals(getDepartmentId(), that.getDepartmentId());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(companyId, departmentId);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "companyId = " + companyId + ", " +
                "departmentId = " + departmentId + ")";
    }
}
