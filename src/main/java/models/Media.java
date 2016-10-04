package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

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
	
}
