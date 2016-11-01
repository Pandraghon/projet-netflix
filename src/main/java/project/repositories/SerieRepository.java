package project.repositories;


import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import project.models.Category;
import project.models.Serie;
public interface SerieRepository extends CrudRepository<Serie, Long> {

	 public Set<Serie> findByMedia_Categories(/*@Param("category")*/ Category category);
}
