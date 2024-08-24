package com.examplesoft.ecommercemonolite.entitylistener.service;

import com.examplesoft.ecommercemonolite.entitylistener.entity.EntityChanges;
import com.examplesoft.ecommercemonolite.entitylistener.entity.ProcessType;
import com.examplesoft.ecommercemonolite.entitylistener.repo.EntityChangesRepository;
import com.examplesoft.ecommercemonolite.security.JwtUtil;
import com.examplesoft.ecommercemonolite.util.BaseEntity;
import jakarta.persistence.PostPersist;
import jakarta.persistence.PostRemove;
import jakarta.persistence.PostUpdate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EntityChangesListener {
    private final EntityChangesRepository repository;

    @PostPersist
    public void postPersist(BaseEntity entity) {
        repository.save(new EntityChanges(ProcessType.POST,entity.getClass().getSimpleName(),entity.getId(), JwtUtil.extractUserId()));
    }

    @PostUpdate
    public void postUpdate(BaseEntity entity) {
        repository.save(new EntityChanges(ProcessType.PUT,entity.getClass().getSimpleName(),entity.getId(),JwtUtil.extractUserId()));
    }

    @PostRemove
    public void postRemove(BaseEntity entity) {
        repository.save(new EntityChanges(ProcessType.DELETE,entity.getClass().getSimpleName(),entity.getId(),JwtUtil.extractUserId()));
    }
}
