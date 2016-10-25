package project.repositories;

import org.springframework.data.repository.CrudRepository;

import project.models.Film;

public interface FilmRepository extends CrudRepository<Film, Long>{

	
}