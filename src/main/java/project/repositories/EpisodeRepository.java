package project.repositories;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;

import project.models.Episode;
import project.models.Video;

public interface EpisodeRepository extends CrudRepository<Episode, Long>{

	public Episode findByVideo(Video video);
}
