package project.repositories;

import org.springframework.data.repository.CrudRepository;

import project.models.Video;

public interface VideoRepository extends CrudRepository<Video, Long>{

}
