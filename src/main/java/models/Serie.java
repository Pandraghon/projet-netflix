package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="series")
public class Serie {

	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="serie_id")
	private Long id;
	
	
	@OneToOne
	@JoinColumn(name="media_id")
	private Media media_id;


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Media getMedia_id() {
		return media_id;
	}


	public void setMedia_id(Media media_id) {
		this.media_id = media_id;
	}


	
}
