package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

@Entity
public class Media {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private long id;
	
	@ManyToMany(mappedBy="wanted")
	private List<User> wanters = new ArrayList<>();
	
	@OneToMany(mappedBy="media")
	private List<Note> notes = new ArrayList<>();
	
}
