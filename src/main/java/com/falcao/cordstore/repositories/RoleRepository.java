package com.falcao.cordstore.repositories;

import java.util.Optional;

import com.falcao.cordstore.models.ERole;
import com.falcao.cordstore.models.Role;
import org.springframework.data.mongodb.repository.MongoRepository;


public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByName(ERole name);
}
