package project.repositories;

import org.springframework.data.repository.CrudRepository;

import project.models.Episode;

public interface EpisodeRepository extends CrudRepository<Episode, Long>{

}
