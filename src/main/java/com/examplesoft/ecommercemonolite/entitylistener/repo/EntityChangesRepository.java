package com.examplesoft.ecommercemonolite.entitylistener.repo;

import com.examplesoft.ecommercemonolite.entitylistener.entity.EntityChanges;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface EntityChangesRepository extends MongoRepository<EntityChanges,String> {
}
