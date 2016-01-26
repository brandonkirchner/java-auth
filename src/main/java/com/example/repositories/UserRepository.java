package com.example.repositories;

import com.example.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(collectionResourceRel = "user", path="user")
public interface UserRepository extends CrudRepository<User, Long> {
    List<User> findByUsername(String username);

    List<User> findByToken(String token);
}
