package project.models;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import project.models.Media;
import project.models.Video;

@Entity
@Table(name="films")
public class Film {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.REFRESH})
	private Media media;
	
	@OneToOne(cascade={CascadeType.PERSIST, CascadeType.REFRESH})
	private Video video;
	
	

	public Film() {
		super();
	}

	public Film(Media media) {
		this.media = media;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Media getMedia() {
		return media;
	}

	public void setMedia(Media media) {
		this.media = media;
	}

	public Video getVideo() {
		return video;
	}

	public void setVideo(Video video) {
		this.video = video;
	}
	
	
	
}