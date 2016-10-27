package project.repositories;

import org.springframework.data.repository.CrudRepository;

import project.models.User;

public interface UserRepository extends CrudRepository<User, Long> {
	
}
