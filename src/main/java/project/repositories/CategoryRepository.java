package project.repositories;

import java.util.Set;
import org.springframework.data.repository.CrudRepository;

import project.models.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    public Category findByName(String name);

    public Category findByNameIgnoreCase(String name);
     
}
