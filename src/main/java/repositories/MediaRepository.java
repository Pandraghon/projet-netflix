package repositories;

import org.springframework.data.repository.CrudRepository;

import models.Media;

public interface MediaRepository extends CrudRepository<Media , Long> {

}
