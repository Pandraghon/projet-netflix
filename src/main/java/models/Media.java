package models;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
//<<<<<<< HEAD
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
//=======
import javax.persistence.Table;
//>>>>>>> branch 'master' of https://github.com/Akim0992/projet-netflix.git

@Entity
public class Media {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	//@Column(name="media_id")
	private long id;
	
	@ManyToMany(mappedBy="wanted")
	private List<User> wanters = new ArrayList<>();
	
	/*@OneToMany(mappedBy="media")
	private List<Note> notes = new ArrayList<>();*/

	@NotNull
	private String name;
	
	@NotNull
	private Date date;
	private String description;
	private String trailer;
	private Blob image;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getTrailer() {
		return trailer;
	}
	public void setTrailer(String trailer) {
		this.trailer = trailer;
	}
	public Blob getImage() {
		return image;
	}
	public void setImage(Blob image) {
		this.image = image;
	}
}
