package project.repositories;

import java.util.Set;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import project.models.Film;
import project.models.Category;

public interface FilmRepository extends CrudRepository<Film, Long>{

    public Set<Film> findByMedia_Categories(/*@Param("category")*/ Category category);
    
}