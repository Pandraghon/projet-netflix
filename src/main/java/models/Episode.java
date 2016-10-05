package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="episodes")
public class Episode {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name="serie_id")
	private Serie series_id;
	
	private Long saison_number;
	
	
	private Long episode_number;
	
	
	@OneToOne
	@JoinColumn(name="video_id")
	private Video video;
	
	
}
