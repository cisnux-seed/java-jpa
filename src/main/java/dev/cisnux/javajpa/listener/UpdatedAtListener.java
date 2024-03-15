package dev.cisnux.javajpa.listener;

import dev.cisnux.javajpa.entity.UpdatedAtAware;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;

import java.time.LocalDateTime;

public class UpdatedAtListener {
    @PrePersist
    @PreUpdate
    public void setLastUpdatedAt(UpdatedAtAware object) {
        object.setUpdatedAt(LocalDateTime.now());
    }
}
