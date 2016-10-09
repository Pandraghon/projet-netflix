package repositories;

import org.springframework.data.repository.CrudRepository;

import models.Film;

public interface FilmRepository extends CrudRepository<Film, Long>{

	
}