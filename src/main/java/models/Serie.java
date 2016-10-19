package models;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="series")
public class Serie {



	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	//@Column(name="serie_id")
	private long id;
	
	
	@OneToOne
	@JoinColumn(name="media_id",referencedColumnName="id")
	private Media media;
	
	public Serie() {
		super();
		// TODO Auto-generated constructor stub
	}


	public Serie(Media media) {
		this.media = media;
		
	}

	
	public long getId() {
		return id;
	}


	public Media getMedia() {
		return media;
	}


	public void setMedia(Media media) {
		this.media = media;
	}


	public void setId(long id) {
		this.id = id;
	}


	@OneToMany(cascade={CascadeType.PERSIST, CascadeType.REFRESH})
	private List<Episode> episodes = new ArrayList<>();
	
	

	public List<Episode> getEpisodes() {
		return episodes;
	}


	public void setEpisodes(List<Episode> episodes) {
		this.episodes = episodes;
	}




	
}
