package project.models;
/*package models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@Table(name="notes")
@IdClass(NoteId.class)
public class Note {

	@Id
	private long media_id;
	
	@Id
	private long user_id;
	
	@Min(0)
	@Max(5)
	private int note;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name="media_id", referencedColumnName="id")
	private Media media;
	
	@ManyToOne
	@PrimaryKeyJoinColumn(name="user_id", referencedColumnName="id")
	private User user;
	
}
*/