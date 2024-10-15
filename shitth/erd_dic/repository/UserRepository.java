package com.packt.cantata.repository;

import com.packt.cantata.domain.User;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface UserRepository extends JpaRepository<User, String>{


	Optional<User> findById(String username);
	Optional<User> findByEmail(String username);
	Optional<User> findByTel(String username);


}
