package com.example.repositories;

import com.example.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "user", path="user")
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    User findByToken(String token);
}
